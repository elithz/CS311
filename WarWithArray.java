// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)


/**	
*	@Filename		WarWithArray.java
*	@Description	WarWithArray for PA1
*	@Version		1.0
*	@Created		10.23.2017 13h05min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/

import java.util.ArrayList;


public class WarWithArray
{
	// member fields and methods
	private String[] data;
	
	public WarWithArray(String[] s, int k)
	{
		// implementation
		data = s;
	}
	
	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < data.length; i ++){
		    for(int j = 0;j < data.length; j ++){
		    	list.add(data[i] + data[j]);
		    }
		}
		return list;
	 }

}

