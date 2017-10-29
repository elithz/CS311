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


public class BinaryST
{
	// member fields and methods
	private int size = 0;
	private int distinctSize = 0;
	private int height;

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
		height = this.height();
	}
	
	public int distinctSize()
	{
		// implementation
//		String[] strF = this.inOrder();
//		int counter = strF.length;
//		for(int i = 0; i < strF.length; i ++){
//			for(int j = i + 1; j < strF.length; j ++){  
//                String num = strF[i];  
//                if(strF[j].compareTo(num) == 0){    
//                    counter --;  
//                    i++;  
//                }  
//            }  
//		}
//		return counter;
		return distictSizeRec(root);
	}
	
	public int distictSizeRec(Node root){
		if(root == null)
			return 0;
		if(root.left == null && root.right == null){
			return 1;
		}
		if(root.left == null && root.key != root.right.key){
			return 1 + this.distictSizeRec(root.right);
		}else if(root.right == null && root.key != root.left.key){
			return 1 + this.distictSizeRec(root.left);
		}else{
			return this.distictSizeRec(root.left) + this.distictSizeRec(root.right);
		}
			
	}
	
	
	public int size()
	{
		// implementation
		return this.sizeRec(root);
	}
	
	public int sizeRec(Node root){
        if(root == null)
            return 0;
         
        // Else recur
        else
        	return 1 + this.sizeRec(root.left) + this.sizeRec(root.right);  
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
		if(searchRec(root, s) == null)
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
		return this.frequencyRec(root, s);
		// implementation
	}
	
	public int frequencyRec(Node root, String key)
    {
        // Base Case
        if(root == null)
            return 0;
 
        if(root.key.compareTo(key) < 0)
            return this.frequencyRec(root.right, key);
                 
        else if(root.key.compareTo(key) > 0)
        	return this.frequencyRec(root.left, key); 
        
        else
        	return 1 + this.frequencyRec(root.left, key) + this.frequencyRec(root.right, key);
        
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
		return (String[]) inOrderStr.toArray(new String[inOrderStr.size()]);
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
		return (String[]) preOrderStr.toArray(new String[preOrderStr.size()]);
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
		// implementation
		if(this.search(s))
			return this.rankRec(root, s);
		else
			return -1;
	}
	
	public int rankRec(Node root, String key)
    {
        // Base Case
        if(root == null)
            return 0;
 
        // If current node is in range, then 
        // include it in count and recur for 
        // left and right children of it
        if(root.key.compareTo(key) < 0)
            return 1 + this.rankRec(root.left, key) + this.rankRec(root.right, key);
       
        else if(root.key.compareTo(key) == 0)
        	return this.rankRec(root.left, key) + this.rankRec(root.right, key);
         
        // Else recur for left child
        else
            return this.rankRec(root.left, key);     
    }
}