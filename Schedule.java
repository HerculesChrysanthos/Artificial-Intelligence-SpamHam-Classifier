import java.io.*;
import java.util.*;
public class Schedule{
	public void createFile(String data,ArrayList<Cell> tiles) {
		File f = null;
		BufferedWriter writer = null;
		int counter=0;
		Cell[][] schedule = new Cell[7*9][5];
		for(int ourcell=0;ourcell<tiles.size();ourcell++){
			for(int day=0;day<5;day++){
				if(tiles.get(ourcell).getDay()==day){
					if(tiles.get(ourcell).getPart()==0)//a1
						schedule[tiles.get(ourcell).getTime()][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==1)//a2
						schedule[tiles.get(ourcell).getTime()+7][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==2)//a3
						schedule[tiles.get(ourcell).getTime()+14][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==3)//b1
						schedule[tiles.get(ourcell).getTime()+21][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==4)//b2
						schedule[tiles.get(ourcell).getTime()+28][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==5)//b3
						schedule[tiles.get(ourcell).getTime()+35][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==6)//g1
						schedule[tiles.get(ourcell).getTime()+42][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==7)//g2
						schedule[tiles.get(ourcell).getTime()+49][day]=tiles.get(ourcell);
					if(tiles.get(ourcell).getPart()==8)//g3
						schedule[tiles.get(ourcell).getTime()+56][day]=tiles.get(ourcell);
				}
			}
		}
		try	{
			f = new File(data);
		}
		catch (NullPointerException e) {
			System.out.println ("Can't create file");
		}

		try	{
			writer = new BufferedWriter(new FileWriter(f));
		}
		catch (IOException e){
			System.out.println("Can't write to file");
		}
		try{
			for(int part=0;part<9;part++){
				if(tiles.get(part*7*5).getPart()==0)
					writer.write("\nA1\n");
				if(tiles.get(part*7*5).getPart()==1)
					writer.write("\nA2\n");
				if(tiles.get(part*7*5).getPart()==2)
					writer.write("\nA3\n");
				if(tiles.get(part*7*5).getPart()==3)
					writer.write("\nB1\n");
				if(tiles.get(part*7*5).getPart()==4)
					writer.write("\nB2\n");
				if(tiles.get(part*7*5).getPart()==5)
					writer.write("\nB3\n");
				if(tiles.get(part*7*5).getPart()==6)
					writer.write("\nG1\n");
				if(tiles.get(part*7*5).getPart()==7)
					writer.write("\nG2\n");
				if(tiles.get(part*7*5).getPart()==8)
					writer.write("\nG3\n");
				for(int i=0;i<schedule[0].length;i++){
					for(int j=7*part;j<7*part+7;j++){
						if(i==0 && j==7*part)
							writer.write("\n\tDeutera\n\n");
						else if(i==1 && j==7*part)
							writer.write("\n\tTrith\n\n");
						else if(i==2 && j==7*part)
							writer.write("\n\tTetarth\n\n");
						else if(i==3 && j==7*part)
							writer.write("\n\tPempth\n\n");
						else if(i==4 && j==7*part)
							writer.write("\n\tParaskeuh\n\n");
						writer.write(schedule[j][i].getTime()+1+") "+schedule[j][i].getLesson().getName()+" By "+schedule[j][i].getTeacher().getName()+"\n");
					}
				}
			}
		}catch(IOException e){
			System.err.println("Write error");
		}
		try {
			writer.close();

		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
}