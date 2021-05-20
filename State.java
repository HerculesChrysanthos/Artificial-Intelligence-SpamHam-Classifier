import java.util.*;
public class State {
	private ArrayList<Cell> tiles = new ArrayList<Cell>();
	private static final int num = 5*7*9;
	private int score;
	
	public State(Lists listes){
		this.score = 0;
		for(int part=0;part<9;part++){
			int[] lessonHour = new int[listes.Lesson_list.size()];
			for(int i=0;i<lessonHour.length;i++)
				lessonHour[i]=listes.Lesson_list.get(i).getHours();
			for(int day=0;day<5;day++){
				for(int hour=0;hour<7;hour++){
					Cell tempCell= new Cell();
					tempCell.setPart(part);
					tempCell.setDay(day);
					tempCell.setTime(hour);
					if(part<3)
						this.tiles.add(InititializeSchedule("a",tempCell,lessonHour,listes));
					else if(part>=3 && part<6)
						this.tiles.add(InititializeSchedule("b",tempCell,lessonHour,listes));
					if(part>=6)
						this.tiles.add(InititializeSchedule("c",tempCell,lessonHour,listes));
				}
			}
		}
		this.calculateScore(listes);
	}
	
	public State(ArrayList<Cell> tile,Lists listes){
		this.score = 0;
		ArrayList<Cell> tilesCopy = new ArrayList<Cell>();
		for(int i=0;i<tile.size();i++){
			Cell c = new Cell();
			c.setDay(tile.get(i).getDay());
			c.setTime(tile.get(i).getTime());
			c.setPart(tile.get(i).getPart());
			c.setTeacher(tile.get(i).getTeacher());
			c.setLesson(tile.get(i).getLesson());
			tilesCopy.add(c);
		}
		this.tiles=tilesCopy;
		calculateScore(listes);
	}
	public Cell InititializeSchedule(String classes,Cell tempCell,int[] lessonHour,Lists listes){
		Random r = new Random();
		Lesson tempLesson = null;
		boolean flagLesson=true;
		int lessonFind=0;
		while(flagLesson){
			tempLesson = listes.Lesson_list.get(r.nextInt(listes.Lesson_list.size()));
			while(!tempLesson.getClasses().equals(classes))
				tempLesson = listes.Lesson_list.get(r.nextInt(listes.Lesson_list.size()));
			for(lessonFind=0;lessonFind<lessonHour.length;lessonFind++){ //xrhsh parallhlou pinaka gia meiwsh wrwn
				if(listes.Lesson_list.get(lessonFind)==tempLesson){
					if((lessonHour[lessonFind]>0 )){
						tempCell.setLesson(tempLesson);
						lessonHour[lessonFind]-=1;
						flagLesson=false;
						break;
					}	
				}
			}
		}
		boolean flagTeacher = true;
		while(flagTeacher){
			Teacher tempTeacher = listes.Teacher_list.get(r.nextInt(listes.Teacher_list.size()));
			for(int teacherIndex=0;teacherIndex<listes.Teacher_list.size();teacherIndex++){
				if(tempTeacher==listes.Teacher_list.get(teacherIndex)){
					for(int j=0;j<listes.Teacher_list.get(teacherIndex).getCourses().length;j++){
						if(listes.Teacher_list.get(teacherIndex).getCourses()[j].equals(tempLesson.getId())){
							tempCell.setTeacher(tempTeacher);
							flagTeacher=false;
							break;
						}
					}
				}
			}
		}
		return tempCell;
	}
	public void calculateScore(Lists listes){
		int violation=0;
		for(int i=0;i<this.tiles.size();i++){
			int adder=1;
			int LessonPerm =1;
			int TeacherFrequency=1;
			for(int j=i+1;j<this.tiles.size();j++){
				if(this.tiles.get(i).getTeacher()==this.tiles.get(j).getTeacher() && this.tiles.get(i).getDay()==this.tiles.get(j).getDay() && this.tiles.get(i).getTime()+1 == this.tiles.get(j).getTime()){	//o idios ka8hghths na mhn exei ma8hma panw apo 2 eres sunexwmena
					for(int k=0;k<this.tiles.size();k++){
						if(this.tiles.get(i).getTeacher()==this.tiles.get(k).getTeacher() && this.tiles.get(i).getDay()==this.tiles.get(k).getDay() && this.tiles.get(i).getTime()+2 == this.tiles.get(k).getTime()){
							violation++;
						}
					}
					TeacherFrequency++;
					if(TeacherFrequency>this.tiles.get(i).getTeacher().getHoursPerDay()) //elegxei poses wres kanei o ka8hghths thn mera
						violation++;
				}
				if(this.tiles.get(i).getTeacher()==this.tiles.get(j).getTeacher()){ //elegxei poses wres kanei o ka8hghths thn vdomada
						adder++;
					if(adder>this.tiles.get(i).getTeacher().getHoursPerWeek())
						violation++;
				}
				if(this.tiles.get(i).getPart()==this.tiles.get(j).getPart() && this.tiles.get(i).getLesson()==this.tiles.get(j).getLesson() && this.tiles.get(i).getDay()==this.tiles.get(j).getDay()){ //thn idia mera den mpainoun oles oi wres tou ma8hmatos
					LessonPerm++;
					if((LessonPerm== 2 && this.tiles.get(i).getLesson().getHours()>=2) || (LessonPerm== 3 && this.tiles.get(i).getLesson().getHours()==3) || (LessonPerm== 4 && this.tiles.get(i).getLesson().getHours()==4)){
						violation++; 
					}
				}
				if(this.tiles.get(i).getPart()==this.tiles.get(j).getPart() && this.tiles.get(i).getLesson()==this.tiles.get(j).getLesson() && this.tiles.get(i).getTeacher()!=this.tiles.get(j).getTeacher()){ //ENAS kathigitis kanei to idio ma8hma se ena tmhma 
					violation++;
				}
				if(this.tiles.get(i).getTeacher()==this.tiles.get(j).getTeacher() && this.tiles.get(i).getDay()==this.tiles.get(j).getDay() && this.tiles.get(i).getTime()==this.tiles.get(j).getTime()){ // o idios kathigitis se mia wra na mhn kanei allou mathima
					violation++;
				}
			}
		}
		this.score=violation;
	}
	
	public  ArrayList<Cell> getTiles(){
		return this.tiles;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	 
	public ArrayList<Cell> SwapHours(int hour1,int hour2,int day1,int day2,int dept,Lists listes){ 
		Cell temp2 = tiles.get(hour2+7*day2 +35*dept);
		Cell swap = tiles.get(hour1+7*day1 +35*dept);
		swap.setTime(hour2);
		swap.setDay(day2);
		
		this.tiles.set(hour2+7*day2 +35*dept,swap);
		temp2.setTime(hour1);
		temp2.setDay(day1);
		this.tiles.set(hour1+7*day1 +35*dept,temp2);
		this.calculateScore(listes);
		return this.tiles;  
	}
	
	public ArrayList<Cell> ChangeTeacher(int hour1,int day1,int dept,Lists listes){
		Cell temp = tiles.get(hour1+7*day1 +35*dept);
		Random r = new Random();
		Lesson tempLess = temp.getLesson();
		boolean flagTeacher = true;
		while(flagTeacher){
			Teacher tempTeacher = listes.Teacher_list.get(r.nextInt(listes.Teacher_list.size()));
			for(int teacherIndex=0;teacherIndex<listes.Teacher_list.size();teacherIndex++){
				if(tempTeacher==listes.Teacher_list.get(teacherIndex)){
					for(int j=0;j<listes.Teacher_list.get(teacherIndex).getCourses().length;j++){
						if(listes.Teacher_list.get(teacherIndex).getCourses()[j].equals(tempLess.getId())){
							temp.setTeacher(tempTeacher);
							this.tiles.set(hour1+7*day1 +35*dept,temp);
							flagTeacher=false;
							break;
						}
					}
				}
			}
		}
		this.calculateScore(listes);
		return this.tiles;
	}
}