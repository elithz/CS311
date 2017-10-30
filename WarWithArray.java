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


//public class WarWithArray
//{
//	// member fields and methods
//	private String[] data;
//	private int lth;
//	
//	public WarWithArray(String[] s, int k)
//	{
//		// implementation
//		data = s;
//		lth = k;
//	}
//	
//	public ArrayList<String> compute2k()
//	{
//		// implementation
//		ArrayList<String> listT = new ArrayList<String>();
//		ArrayList<String> listR = new ArrayList<String>();
//		for(int i = 0; i < data.length; i ++){
//		    for(int j = 0;j < data.length; j ++){
//		    	String raw = data[i] + data[j];
//		    	listT.add(raw);
//		    }
//		}
//		
//		for(int i = 0; i < listT.size(); i ++){
//			ArrayList<String> sublist = new ArrayList<String>();
//			for(int l = 0; 0 <= l && l <= lth; l ++ ){
//				String sbstr = listT.get(i).substring(l, l + lth);
//				sublist.add(sbstr);
//			}
//			
//			for(int n = 0; n < sublist.size(); n++){
//				for(int m = 0; m < data.length; m++){
//					if(data[m].equals(sublist.get(n))){
//						continue;
//					}else{
//						listR.add(listT.get(i));
//						break outter;
//					}
//				}
//			}
//				
//		}
//		return listR;
//	 }
//
//}


import java.util.ArrayList;
import java.util.Arrays;


public class WarWithArray
{
	private String[] data;
	private int size;
	
	public WarWithArray(String[] s, int k)
	{
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
				if(!Arrays.asList(this.data).contains(subStr)) {
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

