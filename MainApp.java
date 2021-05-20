import java.util.*;
public class MainApp{
	public  static void main(String args[]){
		long startTime = System.nanoTime();
		Lists listes = new Lists();
		StoreFiles sf = new StoreFiles();
		sf.LoadFile("Lessons.txt",listes);//Fortonoume to arxio meso tis methodou LoadFile.
		sf.LoadFile("Teachers.txt",listes);//Fortonoume to arxio meso tis methodou LoadFile.
		
		HillClimb hc = new HillClimb();
		State s = hc.HillClimbAlgorithm(10000,listes);
		
		Schedule schedule = new Schedule();
		schedule.createFile("schedule.txt",s.getTiles());
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}