import java.util.*;
import java.io.*;
public class MainApp{
	public  static void main(String args[]){
		StoreFiles sf = new StoreFiles();
		sf.LoadFile();
		
		sf.NBSets();
		System.out.println("\nMultinomial Naive Bayes: \n");
		NaiveBayes nb = new NaiveBayes(sf);
		nb.multinomialNB_Fit(sf);
		nb.multinomialNB_Predict(sf);
		
		sf.ID3Sets();
		System.out.println("ID3: ");
		//Node root = createTree();
		//root.printTree(root, " ");
		Id3 mo = new Id3(sf);
		mo.Id3_Fit(sf);
		
		sf.LRSets();
		System.out.println("\nLogistic Regression: \n");
		LogisticRegression lr = new LogisticRegression(sf);
		lr.LogAlgorithm_Fit(sf);
		lr.LogAlgorithm_Dev(sf);
		lr.LogAlgorithm_Predict(sf);
	}
}