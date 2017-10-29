
public class test2 {

	public test2(){
		
	}
	public static void main(String[] args){
		String[] str = {"V","A","B","C","E"};
		WarWithBST wwa = new WarWithBST(str,1);
		for(int i = 0; i < wwa.compute2k().size(); i++)
			System.out.println(wwa.compute2k().get(i));
	}
}
