import java.util.*;
import java.io.*;
public class LogisticRegression{
	public static final int amountSpamWord = 150;
	public static final int amountHamWord = 10;
	
	public static final int ITERATIONS = 350;
	
	public static final double alpha = 0.001;
	
	public boolean flag = false;
	
	public int [] freq;
	
	public double grad = 100, precisionSpam, recallSpam, F1Spam, acc;
	
	public int [] x;
	public int lbl;
	
	public ArrayList<String> keyListSpam;
	public ArrayList<Integer> valueListSpam;
	
	public ArrayList<String> keyListHam;
	public ArrayList<Integer> valueListHam;

	public ArrayList<String> vocabulary;
	
	public double [] weightArr;
	
	public ArrayList<Email> our_mails;
	
	public LogisticRegression(StoreFiles sf){
		keyListSpam = new ArrayList<String>(sf.wordFromSpam_LR.keySet());
		valueListSpam = new ArrayList<Integer>(sf.wordFromSpam_LR.values());
		
		keyListHam = new ArrayList<String>(sf.wordFromHam_LR.keySet());
		valueListHam = new ArrayList<Integer>(sf.wordFromHam_LR.values());
		
		vocabulary = new ArrayList<String>();
		
		our_mails = new ArrayList<Email>();
		
		weightArr = new double[amountSpamWord+amountHamWord];
		for(int i=0;i<weightArr.length;i++){
			weightArr[i]= 0;
		}
	}
	
	public void LogAlgorithm_Fit(StoreFiles sf){
		int max=0;
		int index=0;
		
		for(int i=0;i<amountSpamWord;i++){
			max=0;
			for(int j=0;j<valueListSpam.size();j++){
				if(max<valueListSpam.get(j)){
					max = valueListSpam.get(j);
					index=j;
				}
			}
			if(keyListHam.contains(keyListSpam.get(index))){
				int tempIndex = keyListHam.indexOf(keyListSpam.get(index));
			}
			vocabulary.add(keyListSpam.get(index));
			valueListSpam.set(index,-max);
		}
		int tempI =amountSpamWord;
		while(tempI<amountHamWord+amountSpamWord){
			max=0;
			for(int j=0;j<valueListHam.size();j++){
				if(max<valueListHam.get(j)){
					max = valueListHam.get(j);
					index=j;
				}
			}
			if(!(vocabulary.contains(keyListHam.get(index)))){
				vocabulary.add(keyListHam.get(index));
				tempI++;
			}
			valueListHam.set(index,-max);
			
		}
	}
	
	public void LogAlgorithm_Dev(StoreFiles sf){
		for(int i=0;i<sf.devSet_LR.size();i++){
			if(sf.devSet_LR.get(i).endsWith("ham.txt"))
				lbl=0;
			else if(sf.devSet_LR.get(i).endsWith("spam.txt"))
				lbl=1;
			String Curr = sf.FileToString(sf.devSet_LR.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			String[] txtToArray = Curr.split(" ");
			x = new int [vocabulary.size()];
			for(int j=0;j<txtToArray.length;j++){
				if(vocabulary.contains(txtToArray[j]))
					x[vocabulary.indexOf(txtToArray[j])]+=1;
			}
			Email e = new Email(lbl,x);
			our_mails.add(e);
		}
		for(int i=0;i<ITERATIONS;i++){
			for(int j=0;j<our_mails.size();j++){
				int [] x = our_mails.get(j).occur_freq_mail;
				double prediction = Classification(x);
				double y = our_mails.get(j).label;
				for(int k=0;k<weightArr.length;k++){
					weightArr[k] += alpha*(y-prediction)*x[k];
				}
				grad +=y*Math.log(Classification(x)) + (1.0-y)*Math.log(1.0-Classification(x));
				if(grad<=0.001){
					flag = true;
					break;
				}
			}
			if(flag)
				break;
		}
	}
	
	public double Classification(int [] words){
		double z = 0;
		for(int i=0;i<weightArr.length;i++){
				z+=weightArr[i]*words[i];
		}
		return Sigmoid(z);
	}
	
	public void LogAlgorithm_Predict(StoreFiles sf){
		boolean HamPredictedRight = false;
		boolean SpamPredictedRight = false;
		
		double true_pos_spam = 0;
		double true_neg_spam = 0;
		
		double false_pos_spam = 0;
		double false_neg_spam = 0;
		
		double realHam=0;
		double realSpam=0;
		for(int i=0;i<sf.testSet_LR.size();i++){
			HamPredictedRight = false;
			SpamPredictedRight = false;
			if(sf.testSet_LR.get(i).endsWith("ham.txt")){
				realHam++;
				HamPredictedRight = true;
			}
			else if(sf.testSet_LR.get(i).endsWith("spam.txt")){
				realSpam++;
				SpamPredictedRight = true;
			}
			String Curr = sf.FileToString(sf.testSet_LR.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			String[] temporary = Curr.split(" ");
			for(int wo =0;wo<temporary.length;wo++){
				String a = temporary[wo].trim();
				temporary[wo]=a;
			}
			freq = new int [vocabulary.size()];
			for(int j=0;j<temporary.length;j++){
				for(int k=0;k<vocabulary.size();k++){
					if(vocabulary.contains(temporary[j]))
						freq[vocabulary.indexOf(temporary[j])]+=1;
				}
			}
			if(Classification(freq)>=0.5){//Spam
				if(SpamPredictedRight)
					true_pos_spam++;//Predicted spam - was spam
				else 
					false_pos_spam++;//Predicted spam - was ham
			}
			else{//Ham
				if(HamPredictedRight)
					true_neg_spam++;//Predicted ham - was ham
				else
					false_neg_spam++;//Predicted ham - was spam
			}				
		}
		precisionSpam = true_pos_spam/(true_pos_spam + false_pos_spam);
		recallSpam = true_pos_spam/(true_pos_spam + false_neg_spam);
		F1Spam = 2*(precisionSpam*recallSpam)/(precisionSpam+recallSpam);
		acc = (true_pos_spam + true_neg_spam)/ sf.testSet_LR.size();
		System.out.println("Spam:\nPrecision: "+precisionSpam*100+" % \nRecall: "+recallSpam*100+" % \nF1: "+F1Spam*100+" %");
		System.out.println("Accuracy: "+acc*100+" % \n");
	}
	
	public double Sigmoid(double zeta){
		return 1.0/(1.0+Math.exp(-zeta));
	}
	
	public static class Email{
		public double label;
		public int [] occur_freq_mail;
		public Email(double label, int [] ocm){
			this.label = label;
			occur_freq_mail = ocm;
		}
	}
}