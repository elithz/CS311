

/**	
*	@Filename		WarWithBST.java
*	@Description	WarWithBST for PA1
*	@Version		1.0
*	@Created		10.23.2017 13h05min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/



//import java.util.ArrayList;
//import java.util.Arrays;
//
//
//public class WarWithBST
//{
//	// member fields and methods
//	private String root;
//	private WarWithBST left;
//	private WarWithBST right;
//	private String[] data;
//	private static	ArrayList<String> str = new ArrayList<String>();
//	private int size;
//	
//	public WarWithBST()
//	{
//		// implementation
//		this.data = null;
//		this.right = null;
//		this.left = null;
//		this.root = null;
//		
//	}
//	public WarWithBST(String s)
//	{
//		// implementation
//		this.data = null;
//		this.right = null;
//		this.left = null;
//		this.root = s;
//		
//	}
//	public WarWithBST(String[] s, int k)
//	{
//		// implementation
//		this.data = s;
//		this.right = null;
//		this.left = null;
//		this.root = null;
//		this.size =k;
//	}
//	
//	
//	public ArrayList<String> compute2k()
//	{
//		// implementation
//		ArrayList<String> twokData = new ArrayList<String>();
//		ArrayList<String> result = new ArrayList<String>();
//		
//		if( this.data != null) 
//		{
//			
//			for(int i =0; i<this.data.length; i++) {
//				for(int j =0; j<this.data.length; j++) {
//					twokData.add(data[i]+data[j]);
//				}
//			}
//			twokData = checkMatch(twokData);
//			
//			result = bstToAL(buildTree(twokData));
//			
//			return result;
//			
//		}
//		else
//		{
//			return null;
//		}		
//		
//	}
//	
//	public  ArrayList<String> checkMatch(ArrayList<String> to2K) {
//		String[] str = new String[to2K.size()];
//		ArrayList<String> result =  new ArrayList<String>();
//		for(int n=0; n<to2K.size(); n++) {
//			str[n]= to2K.get(n);
//		}
//		
//		for(int i=0; i< str.length; i++) {
//			String temp = str[i];
//			for( int j=0; j< temp.length()-this.size+1; j++) {
//				String subStr = temp.substring(j, j+this.size);
//				if(!Arrays.asList(this.data).contains(subStr)) {
//					str[i] = null;
//				}
//			}
//		}
//		
//		for(int j=0; j<str.length; j++) {
//			if(str[j] != null) {
//				result.add(str[j]);
//			}
//		}
//		
//		return result;
//	}
//	
//	public static WarWithBST buildTree(ArrayList<String> s) {
//		WarWithBST bstree = new WarWithBST();
//		
//		for(int i=0; i<s.size(); i++) {
//			bstree.addNode(s.get(i));
//		}
//		return bstree;
//	}
//	
//	public void addNode(String s) {
//		if(this.root == null) {
//			this.root = s;
//		}
//		else {
//			if(this.root.compareTo(s) >0) {
//				if(this.left == null)
//				{
//					this.left = new WarWithBST(s);
//				}
//				else
//				{
//					this.left.addNode(s);
//				}
//			}
//			else {
//				if(this.right ==null)
//				{
//					this.right = new WarWithBST(s);
//				}
//				else
//				{
//					this.right.addNode(s);
//				}
//			}
//		}
//	}
//	
//	public static ArrayList<String> bstToAL(WarWithBST bst) {
//
//		
//		if(bst.root != null)
//		{
//			if(bst.left != null)
//			bstToAL(bst.left);
//			
//			str.add(bst.root);
//			
//			if(bst.right != null)
//			bstToAL(bst.right);
//		}
//		return str;
//	}
//	
//}

import java.util.ArrayList;
import java.util.Arrays;


public class WarWithBST
{
	private BinaryST bst;
	private String[] data;
	private int size;
	
	public WarWithBST(String[] s, int k)
	{
		bst = new BinaryST(s);
		data = s;
		size = k;
		
	}
	
	
	public ArrayList<String> compute2k()
	{
		
		ArrayList<String> listRaw = new ArrayList<String>();
		for(int i = 0; i < data.length; i ++){
		    for(int j = 0;j < data.length; j ++){
		    	String raw = data[i] + data[j];
		    	listRaw.add(raw);
		    }
		}
		
		String[] str = new String[listRaw.size()];
		ArrayList<String> result =  new ArrayList<String>();
		for(int n=0; n<listRaw.size(); n++) {
			str[n]= listRaw.get(n);
		}
		
		for(int i=0; i< str.length; i++) {
			String temp = str[i];
			for( int j=0; j< temp.length()-this.size+1; j++) {
				String subStr = temp.substring(j, j+this.size);
				if(!bst.search(subStr)) {
					str[i] = null;
				}
			}
		}
		
		for(int j=0; j<str.length; j++) {
			if(str[j] != null) {
				result.add(str[j]);
			}
		}
		
		return result;
	 
		
		
	}
	
}


