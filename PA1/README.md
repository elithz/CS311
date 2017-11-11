# CS311
The code of PAs

WarWithArray:
	String[] data;
	int size;
	
	WarWithArray(String[] s, int k)
	{
		data = s;
		size = k;
		
	}
	
	
	ArrayList<String> compute2k()
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
run time: O(n^2) + 2*O(n) + O(n*k)

---------------------------------------------------------------------------------------------------------------------

WarWithBST:
BinaryST bst;
String[] data;
int size;
	
	WarWithBST(String[] s, int k)
	{
		bst = new BinaryST(s);
		data = s;
		size = k;
		
	}
	
	
	ArrayList<String> compute2k()
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
run time: T(n) = O(n^2) + 2*O(n) + O(n*(k+h))
---------------------------------------------------------------------------------------------------------------------

WarWithHash:
private String[] data;
	private int size;
	private static ArrayList<String> str;
	private Hashtable ht;
	
	class hshO{
		int key;
		String value;
	 
		public hshO(String v, int k){
			key = k;
			value = v;
	    }
	}
	
	public WarWithHash(String[] s, int k)
	{
		// implementation
		str = new ArrayList<String>();
		for(int i = 0; i < s.length; i++)
			str.add(s[i]);
		ht = hashFunction(str);
		data = s;
		size = k;
	}
	

	
	public Hashtable hashFunction(ArrayList<String> str) {
		ht = new Hashtable();
		List<hshO> list = new ArrayList<hshO>();
		
		int h = 7;
		for(int i=0; i<str.size(); i++) {
			for(int j =0; j< str.get(i).length(); j++) {
				h = h*31+str.get(i).charAt(j);
			}
			h = h % 23;
			list.add(new hshO(str.get(i), h));
			h=0;
		}
		for( int n=0; n<list.size(); n++) {
			arrHash(list.get(n));
			
		}
		return ht;
		
	}
	
	public void arrHash(hshO hshO) {
		
		if(ht.get(hshO.key) == null) {
			ht.put(hshO.key, hshO.value);
		}
		else {
			arrHash( new hshO(hshO.value, hshO.key+1));
		}
	}

	
	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> result = new ArrayList<String>();

		
		ArrayList<String> listRaw = new ArrayList<String>();
		for(int i = 0; i < data.length; i ++){
		    for(int j = 0;j < data.length; j ++){
		    	String raw = data[i] + data[j];
		    	listRaw.add(raw);
		    }
		}
		
		String[] str = new String[listRaw.size()];

		for(int n=0; n<listRaw.size(); n++) {
			str[n]= listRaw.get(n);
		}
		
		for(int i=0; i< str.length; i++) {
			String temp = str[i];
			for( int j=0; j< temp.length()-this.size+1; j++) {
				String subStr = temp.substring(j, j+this.size);
				if(!ht.contains(subStr)) {
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

run time: T(n) = O(n^2) + 2*O(n) + O(n*(k+1))
---------------------------------------------------------------------------------------------------------------------

WarWithRollHash:
	String[] data;
	int size;
	static ArrayList<String> str;
	Hashtable ht;
	
	class hshO{
		int key;
		String value;
	 
		public hshO(String v, int k){
			key = k;
			value = v;
	    }
	}
	
	WarWithRollHash(String[] s, int k)
	{
		// implementation
		str = new ArrayList<String>();
		for(int i = 0; i < s.length; i++)
			str.add(s[i]);
		ht = hashFunction(str);
		data = s;
		size = k;
	}
	
	
	

	
	Hashtable hashFunction(ArrayList<String> str) {
		ht = new Hashtable();
		List<hshO> list = new ArrayList<hshO>();
	
		int h = 0;
		for(int i=0; i<str.size(); i++) {
			for(int j =0; j< str.get(j).length(); j++) {
				h = h+str.get(i).charAt(j) *3^(str.size()-j);
			}
			h = h % 256;
			list.add(new hshO(str.get(i), h));
			h=0;
		}
		for( int n=0; n<list.size(); n++) {
			arrHash(list.get(n));
				
		}
		return ht;
	
	}

	arrHash(hshO wrh) {
	
		if(ht.get(wrh.key) == null) {
			ht.put(wrh.key, wrh.value);
		}
		else {
			arrHash( new hshO(wrh.value, wrh.key+1));
		}
	}

	
	ArrayList<String> compute2k()
	{
		// implementation

		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<String> listRaw = new ArrayList<String>();
		for(int i = 0; i < data.length; i ++){
		    for(int j = 0;j < data.length; j ++){
		    	String raw = data[i] + data[j];
		    	listRaw.add(raw);
		    }
		}
		
		String[] str = new String[listRaw.size()];
		for(int n=0; n<listRaw.size(); n++) {
			str[n]= listRaw.get(n);
		}
		
		for(int i=0; i< str.length; i++) {
			String temp = str[i];
			for( int j=0; j< temp.length()-this.size+1; j++) {
				String subStr = temp.substring(j, j+this.size);
				if(!ht.contains(subStr)) {
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
run time: T(n) = O(n^2) + 2*O(n) + O(n*(k+1))
