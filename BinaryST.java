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
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BinaryST
{
	// member fields and methods
	private int size = 0;
	private int distinctSize = 0;
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
	}

	public BinaryST()
	{
		// implementation
		root = null;
		size = 0;
		distinctSize = 0;
		height = 0;
	}
	
	public BinaryST(String[] s)
	{
		// implementation
		root = null;
		for(int i = 0;i < s.length;i++)
			this.add(s[i]);
		size = this.size();
		distinctSize = this.distinctSize();
		height = this.height();
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
//		this.height = hOfBST(root);
//		return this.height;
		// implementation
		return heightRec(root);
		
	}
	
	public int heightRec(Node root){
		if (root == null)
            return 0;
        else
        {
            /* compute the depth of each subtree */
            int lDepth = heightRec(root.left);
            int rDepth = heightRec(root.right);
  
            /* use the larger one */
            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }
	}
	
	
//	public int hOfBST(Node node){
//	    if(node == null)
//	        return 0;
//	    else
//	        return 1 +
//	        Math.max(hOfBST(node.left),
//	            hOfBST(node.right));
//	}
	
	
	
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
		if(searchRec(root, s).equals(null))
			return false;
		else
			return true;
	}
	
	public Node searchRec(Node root, String key){
		// Base Cases: root is null or key is present at root
	    if(root == null || root.key.equals(key))
	        return root;
	 
	    // val is greater than root's key
	    if (root.key.compareTo(key) > 0)
	        return searchRec(root.left, key);
	 
	    // val is less than root's key
	    return searchRec(root.right, key);
	}
	
	public int frequency(String s)
	{
		int counter = 0;
		String[] strF = this.inOrder();
		for(int i = 0; i < strF.length; i ++){
			if(strF[i].compareTo(s) == 0)
				counter ++;
		}
		return counter;
		// implementation
	}
	
	public boolean remove(String s)
	{
		// implementation
		root = removeRec(root, s);
		if(root.equals(null))
			return false;
		else{
			this.size --;
			return true;
		}
	}
	
	public Node removeRec(Node root, String key){
		if(root == null)
			return root;
		if(key.compareTo(root.key) < 0)
			root.left = removeRec(root.left, key);
		else if(key.compareTo(root.key) > 0)
			root.right = removeRec(root.right, key);
		
		else{
			if(root.left == null)
				return root.right;
			else if(root.right == null)
				return root.left;
			
			root.key = minValue(root.right);
			
			root.right = removeRec(root.right, root.key);
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