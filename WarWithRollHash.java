// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**	
*	@Filename		WarWithRollHash.java
*	@Description	WarWithRollHash for PA1
*	@Version		1.0
*	@Created		10.23.2017 13h05min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


public class WarWithRollHash
{
	// member fields and methods
	private int size;
	private String[] data;
	private int key;
	private String value;
	private Hashtable ht;
	
	public WarWithRollHash(String[] s, int k)
	{
		// implementation
		this.data = s;
		int temp =1;
		this.size = k*k;
		
	}
	
	public WarWithRollHash(String v, int k)
	{
		// implementation
		this.key =k;
		this.value = v;
	}
	
	public ArrayList<String> build2k() {
		ArrayList<String> to2K = new ArrayList<String>();
		Arrays.sort(this.data);
		Arrays.toString(this.data);
		
		if( this.data != null) 
		{
			for(int i =0; i<this.data.length; i++) {
				for(int j =0; j<this.data.length; j++) {
					to2K.add(data[i]+data[j]);
				}
			}
			
		}

		return to2K;
	}
	
	public Hashtable hashFunction(ArrayList<String> str) {
		ht = new Hashtable();
		List<WarWithRollHash> list = new ArrayList<WarWithRollHash>();
		
		int h = 0;
		for(int i=0; i<str.size(); i++) {
			for(int j =0; j< str.get(j).length(); j++) {
				h = h+str.get(i).charAt(j) *3^(str.size()-j);
			}
			h = h % this.size;
			list.add(new WarWithRollHash(str.get(i), h));
			h=0;
		}
		for( int n=0; n<list.size(); n++) {
			arrHash(list.get(n));
			
		}
		return ht;
	}
	
	public void arrHash(WarWithRollHash wrh) {
		
		if(ht.get(wrh.key) == null) {
			ht.put(wrh.key, wrh.value);
		}
		else {
			arrHash( new WarWithRollHash(wrh.value, wrh.key+1));
		}
	}
	
	public boolean search(String s) {
		ArrayList<String> str = build2k();
		Hashtable ht = hashFunction(str);
		boolean check = str.contains(s);
		
		return check;
	}
	
	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> str = build2k();
		Hashtable ht = hashFunction(str);
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0; i<ht.size(); i++) {
			result.add((String) ht.get(i));
		}
		
		return result;
	}
}



