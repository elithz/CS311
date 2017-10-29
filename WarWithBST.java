

/**	
*	@Filename		WarWithBST.java
*	@Description	WarWithBST for PA1
*	@Version		1.0
*	@Created		10.23.2017 13h05min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/

import java.util.ArrayList;


public class WarWithBST
{
	private BinaryST bst;

	public WarWithBST(String[] s, int k)
	{
		bst = new BinaryST(s);
	}
	
	
	public ArrayList<String> compute2k()
	{
		String[] str = bst.inOrder();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < str.length; i ++){
		    for(int j = 0;j < str.length; j ++){
		    	list.add(str[i] + str[j]);
		    }
		}
		return list;
		
	}
	
}

