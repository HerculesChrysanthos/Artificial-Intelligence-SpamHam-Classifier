import java.util.*;
import java.io.*;
public class NaiveBayes{
	public static final int amountSpamWord = 160;
	public static final int amountHamWord =10;
	
	public ArrayList<String> keyListSpam;
	public ArrayList<Integer> valueListSpam;
	
	public ArrayList<String> keyListHam;
	public ArrayList<Integer> valueListHam;
	
	public ArrayList<String> totalKeyList;
	
	public ArrayList<String> vocabulary;
	public ArrayList<Vector> totalFreq;
	public double[][] wordsLikelihood_spam; //the rows are for the words of the vocabulary and the columns 1)for occurence of the word in every txt of spams and 2) for conditional propability of the word in spam category
	public double[][] wordsLikelihood_ham; //the rows are for the words of the vocabulary and the columns 1)for occurence of the word in every txt of hams and 2) for conditional propability of the word in ham category
	double probability_spamTxt;
	double probability_hamTxt;
	public double precisionSpam, recallSpam, F1Spam, precisionHam, recallHam, F1Ham;
	public NaiveBayes(StoreFiles sf){
		keyListSpam = new ArrayList<String>(sf.wordFromSpam.keySet());
		valueListSpam = new ArrayList<Integer>(sf.wordFromSpam.values());
		
		keyListHam = new ArrayList<String>(sf.wordFromHam.keySet());
		valueListHam = new ArrayList<Integer>(sf.wordFromHam.values());
		totalKeyList = new ArrayList<String>();
		
		vocabulary = new ArrayList<String>();
		totalFreq = new ArrayList<Vector>();
		wordsLikelihood_spam = new double[amountSpamWord+amountHamWord][2];
		wordsLikelihood_ham = new double[amountSpamWord+amountHamWord][2]; 
		probability_spamTxt=0;
		probability_hamTxt=0;

	}
	public void multinomialNB_Fit(StoreFiles sf){
		
		probability_spamTxt = sf.getSpamCount()/(sf.trainSet.size()); 
		probability_hamTxt = sf.getHamCount()/sf.trainSet.size(); 
		
		double totalSpamWords = 0;//total spam words 
		double totalHamWords = 0;//total ham words 
		
		for(int i=0;i<valueListSpam.size();i++){
			totalSpamWords += valueListSpam.get(i);
		}
		for(int i=0;i<valueListHam.size();i++){
			totalHamWords += valueListHam.get(i);
		}
		
		totalKeyList = (ArrayList)keyListSpam.clone();
		for(int i=0;i<keyListHam.size();i++){
			if(!(totalKeyList.contains(keyListHam.get(i)))){
				totalKeyList.add(keyListHam.get(i));
			}
		}
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
			wordsLikelihood_spam[i][0] = valueListSpam.get(index);
			if(keyListHam.contains(keyListSpam.get(index))){
				int tempIndex = keyListHam.indexOf(keyListSpam.get(index));
				wordsLikelihood_ham[i][0] = valueListHam.get(tempIndex);
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
				wordsLikelihood_ham[tempI][0] = valueListHam.get(index);
				if(keyListSpam.contains(valueListHam.get(index))){
					int tempIndex = keyListSpam.indexOf(keyListHam.get(index));
					wordsLikelihood_spam[tempI][0] = valueListSpam.get(tempIndex);
				}
				vocabulary.add(keyListHam.get(index));
				tempI++;
			}
			valueListHam.set(index,-max);
		}
	//	for(int i=0;i<vocabulary.size();i++){
	//		System.out.println(vocabulary.get(i));
	//	}
		for(int i=0;i<vocabulary.size();i++){
			wordsLikelihood_spam[i][1] = (wordsLikelihood_spam[i][0] + 1)/(totalSpamWords + totalKeyList.size());
			wordsLikelihood_ham[i][1] = (wordsLikelihood_ham[i][0] + 1)/(totalHamWords + totalKeyList.size());
		}
		
	}
	public void multinomialNB_Predict(StoreFiles sf){
		Vector frequency=null;
		double predictedSpam=0;
		double predictedHam=0;
		double realHam=0;
		double realSpam=0;
		double truePositive = 0;
		double falsePositive =0;
		double falseNegative = 0;
		double trueNegative = 0;
		for(int i=0;i<sf.testSet.size();i++){
			if(sf.testSet.get(i).endsWith("ham.txt")){
				realHam++;
			}
			else{
				realSpam++;
			}
			double SpamProbabilty = 0;
			double HamProbabilty = 0;
			frequency = new Vector();
			for(int f=0;f<vocabulary.size();f++) {
				frequency.add(0);
			}
			String Curr = sf.FileToString(sf.testSet.get(i));
			Curr = Curr.replaceAll("[\\s+]", " ");
			Curr = Curr.replaceAll("[^a-zA-Z]", " ");
			String[] txtToArray = Curr.split(" ");
			for(int wo =0;wo<txtToArray.length;wo++){
				String a = txtToArray[wo].trim();
				txtToArray[wo]=a;
			}
			for(int w=0;w<txtToArray.length;w++){
				if(vocabulary.contains(txtToArray[w])){
					int vocIndex = vocabulary.indexOf(txtToArray[w]);
					frequency.set(vocIndex,1);
				}
			} 
			SpamProbabilty = probability_spamTxt;
			HamProbabilty = probability_hamTxt;
			for(int j=0;j<frequency.size();j++){
				if((Integer)frequency.get(j)==1){
					SpamProbabilty *= wordsLikelihood_spam[j][1];
					HamProbabilty *= wordsLikelihood_ham[j][1];
				}
			}
			if(SpamProbabilty>=HamProbabilty){
				predictedSpam++;
				if(sf.testSet.get(i).endsWith("spam.txt"))
					truePositive++;
				else
					falsePositive++;
			}
			else{
				predictedHam++;
				if(sf.testSet.get(i).endsWith("ham.txt"))
					trueNegative++;
				else
					falseNegative++;
			}
			precisionSpam = truePositive/(truePositive+falsePositive);
			recallSpam = truePositive/(truePositive+falseNegative);
			F1Spam = 2*(precisionSpam*recallSpam)/(precisionSpam+recallSpam);
		}
		System.out.println("# test emails "+sf.testSet.size());
		System.out.println("Spam:\nPrecision: "+precisionSpam*100+" %\nRecall: "+recallSpam*100+" %\nF1: "+F1Spam*100+ " %");
		System.out.println("Accuracy "+((truePositive+trueNegative)/sf.testSet.size())*100+" %\n");
	}
}