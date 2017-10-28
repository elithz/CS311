// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**	
*	@Filename		BinaryST
*	@Description	BST for PA1
*	@Version		1.0
*	@Created		10.23.2017 13h05min23s
*	@Author			elith(Ningyuan Zhang)
*	@Company		NERVE Software
*/

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BinaryST
{
	// member fields and methods
	private int size;
	private int height;
	private ArrayList<String> str;
	//private boolean search;
	//private int frequency;
	//private boolean remove;
	private Node root;
	private ArrayList<String> inOrderStr = new ArrayList<String>();
	private ArrayList<String> preOrderStr = new ArrayList<String>();
	
	class Node{
		String key;
		Node left, right;
	 
		public Node(String item){
			key = item;
	        left = right = null;
	    }
		
		public boolean search(String key) {
            if (key == this.key)
                  return true;
            else if (key.compareTo(root.key) < 0) {
                  if (left == null)
                        return false;
                  else
                        return left.search(key);
            } else if (key.compareTo(root.key) >= 0) {
                  if (right == null)
                        return false;
                  else
                        return right.search(key);
            }
            return false;
		}
		
	}

	public BinaryST()
	{
		// implementation
		root = null;
	}
	
	public BinaryST(String[] s)
	{
		// implementation
		root = null;
		size = 0;
		for(int i = 0;i < s.length;i++)
			this.add(s[i]);
	}
	
	public int distinctSize()
	{
		return 0;
		// implementation
	}
	
	public int size()
	{
		return this.size;
		// implementation
	}
	
	public int height()
	{
		this.height = hOfBST(root);
		return this.height;
		// implementation
	}
	
	
	public int hOfBST(Node node){
	    if(node == null)
	        return 0;
	    else
	        return 1 +
	        Math.max(hOfBST(node.left),
	            hOfBST(node.right));
	}
	
	
	
	public void add(String s)
	{
		// implementation
		root = addRec(root, s);
		size ++;
	}
	
	public Node addRec(Node root, String key){
		if(root == null){
			root = new Node(key);
			return root;
		}
		if(key.compareTo(root.key) < 0)
			root.left = addRec(root.left, key);
		else if(key.compareTo(root.key) >= 0)
			root.right = addRec(root.right, key);
		return root;
	}
	
	public boolean search(String s)
	{
		// implementation
		 if (root == null)
             return false;
         else
             return root.search(s);
	}
	
	public int frequency(String s)
	{
		return 0;
		// implementation
	}
	
	public boolean remove(String s)
	{
		
		return false;
		// implementation
	}
	
	public Node deleteRec(Node root, String key){
		if(root == null)
			return root;
		if(key.compareTo(root.key) < 0)
			root.left = deleteRec(root.left, key);
		else if(key.compareTo(root.key) > 0)
			root.right = deleteRec(root.right, key);
		
		else{
			if(root.left == null)
				return root.right;
			else if(root.right == null)
				return root.left;
			
			root.key = minValue(root.right);
			
			root.right = deleteRec(root.right, root.key);
		}
		
		return root;
		
	}
	
	public String minValue(Node root){
		String minv = root.key;
		while(!root.left.key.equals(null)){
			minv = root.left.key;
			root = root.left;
		}
		return minv;
	}
	
	public String[] inOrder()
	{
		// implementation
		inOrderStr.clear();
		inOrderRec(root);
		return (String[]) inOrderStr.toArray();
	}
	
	public void inOrderRec(Node root){
		if(root != null){
			inOrderRec(root.left);
			inOrderStr.add(root.key);
			inOrderRec(root.right);
		}
	}
	
	public String[] preOrder()
	{
		// implementation
		preOrderStr.clear();
		preOrderRec(root);
		return (String[]) preOrderStr.toArray();
	}
	
	public void preOrderRec(Node root){
		if(root != null){
			preOrderStr.add(root.key);
			preOrderRec(root.left);
			preOrderRec(root.right);
		}
	}
	
	public int rankOf(String s)
	{
		return 0;
		// implementation
	}
}