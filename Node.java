import java.util.*;
import java.io.*;
public class Node {
    String word;
    Node left;
    Node right;
    ArrayList<Vector> totVec;
 public boolean hasRight(){
 	if(right==null)
 		return false;
 	else
 		return true;
 }
 public boolean hasLeft(){
 	if(left==null)
 		return false;
 	else
 		return true;
 }
 public String getWord(){
 	return this.word;
 }
 public void addRight(Node right){
 	this.right=right;
 }
 public void addLeft(Node left){
 	this.left=left;
 }
   public Node(String word,ArrayList<Vector> totVec) {
        this.word = word;
        right = null;
        left = null;
        totVec=this.totVec;
    }
}