import java.util.*;
public class Lists{
	public ArrayList<Lesson> Lesson_list = new ArrayList<Lesson>();
	public ArrayList<Teacher> Teacher_list = new ArrayList<Teacher>();
	
	public void addLesson(Lesson les){ 
		Lesson_list.add(les);
	}
	public void ListLessons(){ 
		for(Lesson les: Lesson_list)
			System.out.println(les.toStringLesson());
	} 
	public void addTeacher(Teacher t){
		Teacher_list.add(t);
	}
	public void ListTeachers(){
		for(Teacher t: Teacher_list)
			System.out.println(t.toStringTeacher());
	}
}