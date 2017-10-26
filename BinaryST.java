// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**	
*	@Filename		baMng.c
*	@Description	Bank Account Manage Server
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
	//private boolean search;
	//private int frequency;
	//private boolean remove;

	 class Node{
		 int key;
		 Node left, right;
	 
		 public Node(int item){
			 key = item;
	         left = right = null;
	     }
	  }

	public BinaryST()
	{
		// implementation
	}
	
	public BinaryST(String[] s)
	{
		// implementation
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
		return this.height;
		// implementation
	}
	
	public void add(String s)
	{
		// implementation
	}
	
	public boolean search(String s)
	{
		return false;
		// implementation
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
	
	public String[] inOrder()
	{
		return null;
		// implementation
	}
	
	public String[] preOrder()
	{
		return null;
		// implementation
	}
	
	public int rankOf(String s)
	{
		return 0;
		// implementation
	}
}