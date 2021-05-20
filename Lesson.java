import java.util.*;
public class Lesson{
	private String Id;
	private String Name;
	private String Classes;
	private int Hours;
	
	public Lesson(String Id,String Name,String Classes,int Hours){
		this.Id=Id;
		this.Name=Name;
		this.Classes=Classes;
		this.Hours=Hours;
	}
	public void setId(String Id){
		this.Id=Id;
	}
	public void setName(String Name){
		this.Name=Name;
	}
	public void setClasses(String Classes){
		this.Classes=Classes;
	}
	public void setHours(int Hours){
		this.Hours=Hours;
	}
	public String getId(){
		return Id;
	}
	public String getName(){
		return Name;
	}
	public String getClasses(){
		return Classes;
	}
	public int getHours(){
		return Hours;
	}
	public String toStringLesson(){
		return "id "+getId()+" name "+getName()+" class "+getClasses()+" hours "+getHours();
	}
}