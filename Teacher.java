public class Teacher{
	private String Id;
	private String Name;
	private String[] Courses;
	private int HoursPerDay;
	private int HoursPerWeek;
	
	public Teacher(String Id,String Name,String[] Courses,int HoursPerDay,int HoursPerWeek){
		this.Id=Id;
        this.Name=Name;
        this.Courses=Courses;
        this.HoursPerDay=HoursPerDay;
        this.HoursPerWeek=HoursPerWeek;
    }
    public void setId(String Id){
        this.Id=Id;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public void setCourses(String[] Courses){
        this.Courses=Courses;
    }
    public void setHoursPerDay(String Hours){
        this.HoursPerDay=HoursPerDay;
    }
    public void setHoursPerWeek(String Hours){
        this.HoursPerWeek=HoursPerWeek;
    }
    public String getId(){
        return Id;
    }
    public String getName(){
        return Name;
    }
    public String[] getCourses(){
        return Courses;
    }
    public int getHoursPerDay(){
        return HoursPerDay;
    }
    public int getHoursPerWeek(){
        return HoursPerWeek;
    }
	public String toStringTeacher(){
		return "id "+getId()+" name "+getName()+" hours/d "+getHoursPerDay()+" hours/w "+getHoursPerWeek();
	}
}
