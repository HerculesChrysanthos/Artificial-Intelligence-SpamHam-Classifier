import java.util.*;
public class HillClimb{
	public State HillClimbAlgorithm(int maxSteps,Lists listes){
		State CurrentState = new State(listes);
		System.out.println(CurrentState.getScore());
		int step=0;
		while(step < maxSteps){
			Random r = new Random();
			int hour1 = r.nextInt(7);
			int hour2 = r.nextInt(7);
			int day1 = r.nextInt(5);
			int day2 = r.nextInt(5);
			int dept = r.nextInt(9);
			
			State child = new State(CurrentState.getTiles(),listes);
			int t = r.nextInt(2);
			if(t == 0){
				while(hour1==hour2 && day1==day2){
					hour1 = r.nextInt(7);
					hour2 = r.nextInt(7);
					day1 = r.nextInt(5);
					day2 = r.nextInt(5);
				}
				child.SwapHours(hour1,hour2,day1,day2,dept,listes);
			}
			else{
				child.ChangeTeacher(hour1,day1,dept,listes);
			}
			step++;
			if(child.getScore() < CurrentState.getScore()){
				System.out.println("new score: "+child.getScore());
				step=0;
				CurrentState = new State(child.getTiles(),listes);
			}
		}
		System.out.println("Trexon score: "+CurrentState.getScore());
		return CurrentState;
	}
}