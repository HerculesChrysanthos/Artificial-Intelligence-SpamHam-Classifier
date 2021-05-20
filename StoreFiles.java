import java.io.*;
import java.nio.file.Files; 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StoreFiles{
	public static final String newFileName = "Email";
	public static final String[] stopWords = {"","a", "about", "above", "across", "after", "afterwards", 
											"again", "all", "almost", "alone", "along", "already", "also",    
											"although", "always", "am", "among", "amongst", "amoungst", "amount", "an", 
											"and", "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", 
											"as", "at", "be", "became", "because", "become","becomes", "becoming", "been", "before", 
											"behind", "being", "beside", "besides", "between", "beyond", "both", "but", "by","can", 
											"cannot", "cant", "could", "couldnt", "de", "describe", "do", "done", "each", "eg", "either", 
											"else", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere",
											"except", "few", "find","for","found", "four", "from", "further", "get", "give", "go", "had", 
											"has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers",
											"herself", "him", "himself", "his", "how", "however", "i", "ie", "if", "in", "indeed", "is", "it", "its",
											"itself", "keep", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mine", 
											"more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "neither", "never", 
											"nevertheless", "next","no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of",
											"off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", 
											"ourselves", "out", "over", "own", "part","perhaps", "please", "put", "rather", "re", "same", "see", "seem", 
											"seemed", "seeming", "seems", "she", "should","since", "sincere","so", "some", "somehow", "someone", "something", 
											"sometime", "sometimes", "somewhere", "still", "such", "take","than", "that", "the", "their", "them", 
											"themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they",
											"this", "those", "though", "through", "throughout","thru", "thus", "to", "together", "too", "toward", "towards",
											"under", "until", "up", "upon", "us","very", "was", "we", "well", "were", "what", "whatever", "when",
											"whence", "whenever", "where", "whereafter", "whereas", "whereby","wherein", "whereupon", "wherever", "whether", "which", "while", 
											"who", "whoever", "whom", "whose", "why", "will", "with","within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves",
											"b","c","d","e","f","g","h","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","td","tr","th","html","subject"}; 		//maybe abc must be included
	
	public ArrayList<String> emails = new ArrayList<String>();
	public HashMap<String,Integer> wordFromHam = new HashMap<String,Integer>();
	public HashMap<String,Integer> wordFromSpam = new HashMap<String,Integer>();
	public ArrayList<String> testSet = new ArrayList<String>();
	public ArrayList<String> trainSet = new ArrayList<String>();
	
	public ArrayList<String> emails_Id3 = new ArrayList<String>();
	public HashMap<String,Integer> wordFromHam_Id3 = new HashMap<String,Integer>();
	public HashMap<String,Integer> wordFromSpam_Id3 = new HashMap<String,Integer>();
	public ArrayList<String> testSet_Id3 = new ArrayList<String>();
	public ArrayList<String> trainSet_Id3 = new ArrayList<String>();
	public ArrayList<String[]> totalTextToArray = new ArrayList<String[]>();
	public ArrayList<Integer> typeFile = new ArrayList<Integer>();
	
	public ArrayList<String> emails_LR = new ArrayList<String>();
	public HashMap<String,Integer> wordFromHam_LR = new HashMap<String,Integer>();
	public HashMap<String,Integer> wordFromSpam_LR = new HashMap<String,Integer>();
	public ArrayList<String> testSet_LR = new ArrayList<String>();
	public ArrayList<String> trainSet_LR = new ArrayList<String>();
	public ArrayList<String> devSet_LR = new ArrayList<String>();
	
	double SpamCount = 0;
	double HamCount = 0;
	
	
	public void LoadFile(){
		File dir = new File(newFileName);
		if(!dir.mkdir())
			deleteDirFolder(dir);
		dir.mkdir();
		try { 
			File f = new File("enron1"); 
			FilenameFilter filter = new FilenameFilter() { 
				public boolean accept(File f, String name) { 
					return !name.endsWith("txt"); 
				} 
			}; 
			File[] files = f.listFiles(filter); 
		
			System.out.println("Files are:"); 
			for (int i = 0; i <files.length; i++) { 
				System.out.println(files[i].getName());
				String file_name = files[i].getName();
				File f_in = new File("enron1"+"/"+files[i].getName()); 
				
				File[] files_new = f_in.listFiles(); 
				for(int j=0;j<files_new.length;j++){
					moveFiles("enron1"+"/"+files[i].getName()+"/"+files_new[j].getName(),newFileName+"/"+files_new[j].getName());
					emails.add(newFileName+"/"+files_new[j].getName());
					emails_Id3.add(newFileName+"/"+files_new[j].getName());
					emails_LR.add(newFileName+"/"+files_new[j].getName());
				}
			}
		}catch(NullPointerException e){
			System.err.println("The file was not found.");
		}
	}
	
	public void NBSets(){
		SpamCount = 0;
		HamCount = 0;
		Random r = new Random();
		int randomEmail =0;
		int totalTest = (int)(0.4* emails.size());
		int totalTrain =  emails.size() - totalTest;
		
		for(int i=0;i<totalTest;i++){
			randomEmail = r.nextInt( emails.size());
			testSet.add( emails.get(randomEmail));
			emails.remove(randomEmail);
		}
		trainSet = (ArrayList) emails.clone();
		for(int i=0;i<totalTrain;i++){
			String Curr = FileToString(trainSet.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			Curr = Curr.toLowerCase();
			String[] txtToArray = Curr.split(" ");
			for(int wo =0;wo<txtToArray.length;wo++){
				String a = txtToArray[wo].trim();
				txtToArray[wo]=a;
			}
			if(trainSet.get(i).endsWith("ham.txt")){
				typeFile.add(0);	
				HamCount++;
				int temp;
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromHam.containsKey(txtToArray[k])){
						temp =  wordFromHam.get(txtToArray[k]) +1;
						wordFromHam.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromHam.put(txtToArray[k],1);
						}
					}
				}
			}
			else if(trainSet.get(i).endsWith("spam.txt")){
				SpamCount++;
				typeFile.add(1);	
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromSpam.containsKey(txtToArray[k])){
						int temp =  wordFromSpam.get(txtToArray[k]) + 1;
						wordFromSpam.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromSpam.put(txtToArray[k],1);
						}
					}
				}
			}
		}
	}
	public void ID3Sets(){
		SpamCount = 0;
		HamCount = 0;
		Random r = new Random();
		int randomEmail =0;
		int totalTest = (int)(0.4* emails_Id3.size());
		int totalTrain =  emails_Id3.size() - totalTest;
		for(int i=0;i<totalTest;i++){
			randomEmail = r.nextInt( emails_Id3.size());
			testSet_Id3.add( emails_Id3.get(randomEmail));
			emails_Id3.remove(randomEmail);
		}
		trainSet_Id3 = (ArrayList) emails_Id3.clone();
		for(int i=0;i<totalTrain;i++){
			String Curr = FileToString(trainSet_Id3.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			Curr = Curr.toLowerCase();
			String[] txtToArray = Curr.split(" ");
			totalTextToArray.add(txtToArray);
			for(int wo =0;wo<txtToArray.length;wo++){
				String a = txtToArray[wo].trim();
				txtToArray[wo]=a;
			}
			if(trainSet_Id3.get(i).endsWith("ham.txt")){
				typeFile.add(0);
				HamCount++;
				int temp;
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromHam_Id3.containsKey(txtToArray[k])){
						temp =  wordFromHam_Id3.get(txtToArray[k]) +1;
						wordFromHam_Id3.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromHam_Id3.put(txtToArray[k],1);
						}
					}
				}
			}
			else if(trainSet_Id3.get(i).endsWith("spam.txt")){
				SpamCount++;
				typeFile.add(1);
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromSpam_Id3.containsKey(txtToArray[k])){
						int temp =  wordFromSpam_Id3.get(txtToArray[k]) + 1;
						wordFromSpam_Id3.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromSpam_Id3.put(txtToArray[k],1);
						}
					}
				}
			}
		}
	}
	public void LRSets(){
		SpamCount = 0;
		HamCount = 0;
		Random r = new Random();
		int randomEmail =0;
		int totalTest = (int)(0.2* emails_LR.size());
		int totalDev = (int)(0.3* emails_LR.size());
		int totalTrain =  emails_LR.size() - totalTest - totalDev;
		for(int i=0;i<totalTest;i++){
			randomEmail = r.nextInt( emails_LR.size());
			testSet_LR.add( emails_LR.get(randomEmail));
			emails_LR.remove(randomEmail);
		}
		//devSet;
		for(int i=0;i<totalDev;i++){
			randomEmail = r.nextInt( emails_LR.size());
			devSet_LR.add( emails_LR.get(randomEmail));
			emails_LR.remove(randomEmail);
		}
		trainSet_LR = (ArrayList) emails_LR.clone();
		for(int i=0;i<totalTrain;i++){
			String Curr = FileToString(trainSet_LR.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			Curr = Curr.toLowerCase();
			String[] txtToArray = Curr.split(" ");
			for(int wo =0;wo<txtToArray.length;wo++){
				String a = txtToArray[wo].trim();
				txtToArray[wo]=a;
			}
			if(trainSet_LR.get(i).endsWith("ham.txt")){
				HamCount++;
				int temp;
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromHam_LR.containsKey(txtToArray[k])){
						temp =  wordFromHam_LR.get(txtToArray[k]) +1;
						wordFromHam_LR.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromHam_LR.put(txtToArray[k],1);
						}
					}
				}
			}
			else if(trainSet_LR.get(i).endsWith("spam.txt")){
				SpamCount++;
				for(int k=0;k<txtToArray.length;k++){
					if(wordFromSpam_LR.containsKey(txtToArray[k])){
						int temp =  wordFromSpam_LR.get(txtToArray[k]) + 1;
						wordFromSpam_LR.replace(txtToArray[k],temp);
					}
					else{
						boolean flag=true;
						for(int j=0;j<stopWords.length;j++){
							if(txtToArray[k].equals(stopWords[j])){
								flag=false;
								break;
							}
						}
						if(flag){
							wordFromSpam_LR.put(txtToArray[k],1);
						}
					}
				}
			}
		}
	}
	public double getSpamCount(){
		return SpamCount;
	}
	public double getHamCount(){
		return HamCount;
	}
	public void deleteDirFolder(File dir){
		File[] subFiles = dir.listFiles();
		for(File f: subFiles){
			if(f.isDirectory())
				deleteDirFolder(f);
			f.delete();
		}
	}
	public String FileToString(String filePath){
		String temp ="";
		try{
			temp = new String(Files.readAllBytes(Paths.get(filePath)));
		}catch(IOException e) {
			System.out.println("Exception while reading file: " + e.getMessage());
		}
		return temp;
	}
	public void moveFiles(String source,String dest ) {
		Path sourceFile = Paths.get(source);
		Path targetFile = Paths.get(dest);
		try{
			Files.copy(sourceFile, targetFile); 
		}catch(IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}
    } 
}