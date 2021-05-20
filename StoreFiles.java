import java.io.*;
import java.util.*;
public class StoreFiles{
	public void LoadFile(String a,Lists listes){
		String line;
		File f= null;
		try{
			f = new File(a);//ypodoxi tou arxiou.
		}
		catch(NullPointerException e){
			System.err.println("The file was not found.");
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader(f));
			line = reader.readLine();
			int i=-1;
			int counter=0;
			while(line!=null){
				String id="",temp="",name="",Classes="";
				int hours=0;
				if(line.startsWith("1") || line.startsWith("2") || line.startsWith("3")){
					i =- 1;
					counter = 0;
					while(counter<4){
						i++;
						while(((line.charAt(i))!=',')){
							temp += line.charAt(i);
							i++;
						}
						counter++;
						if(counter==1)
							id = temp;
						else if(counter==2)
							name = temp;
						else if(counter==3)
							Classes = temp;
						else
							hours= Integer.parseInt(temp);
						temp="";
					}
					Lesson tempLesson=new Lesson(id,name,Classes,hours);
					listes.addLesson(tempLesson);
					
				}
				else if(line.startsWith("4")){
					
				int k =0;
					i=-1;
					counter=0;
					int FirstDollar=0,SecondDollar=0;
					while(counter<3){
						i++;
						while((line.charAt(i)!=',')){
							temp+=line.charAt(i);
							i++;
							
						}
						counter++;
						if(counter==1)
							id=temp;
						else if(counter==2)
							name=temp;
						else{
							for(k = 0;k<line.length();k++){
								
								if(line.charAt(k)=='$'){
									if(FirstDollar==0)
										FirstDollar = k;
									else
										SecondDollar = k;
								}
							}
							String ttemp = line.substring(i-counter,FirstDollar-1);
							String[] Courses = ttemp.split(",");
							int HoursPerDay = Integer.parseInt(line.substring(FirstDollar+1,SecondDollar-1));
							int HoursPerWeek = Integer.parseInt(line.substring(SecondDollar+1,line.length()));
							Teacher tempTeacher = new Teacher(id,name,Courses,HoursPerDay,HoursPerWeek);
							listes.addTeacher(tempTeacher);
						}
						temp="";
					}
				}
				else{
					System.out.println("Wrong code type...");
					break;
				}
				
				line = reader.readLine();
			}
		}
		catch(IOException e){
			System.out.println("Could not open file.");
		}
	}
}