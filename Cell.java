public class Cell{
	private Teacher teacher_info;
	private Lesson lesson_info;
	private int day, time, part;
	public Cell(){
		
	}
	public Cell(int day,int time,int part,Lesson lesson_info,Teacher teacher_info){
		this.day=day;
		this.time=time;
		this.part=part;
		this.lesson_info=lesson_info;
		this.teacher_info=teacher_info;
	}
	public Teacher getTeacher(){
		return teacher_info;
	}
	public Lesson getLesson(){
		return lesson_info;
	}
	public int getDay(){
		return day;
	}
	public int getTime(){
		return time;
	}
	public int getPart(){
		return part;
	}
	public void setTeacher(Teacher t){
		teacher_info = t;
	}
	public void setLesson(Lesson l){
		lesson_info = l;
	}
	public void setDay(int d){
		day = d;
	}
	public void setTime(int t){
		time = t;
	}
	public void setPart(int p){
		part = p;
	}
	
	public String toStringCell(){
		return " mera "+getDay()+" wra "+getTime()+" tmhma " +getPart()+" teacher "+getTeacher().getName()+" lesson  "+getLesson().getName();
	}

}	