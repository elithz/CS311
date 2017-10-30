import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class test2 {

	public test2(){
		
	}
	public static void main(String[] args){
		String[] str = {"AA","AC","CC"};
		WarWithRollHash wwa = new WarWithRollHash(str,2);
		for(int i = 0; i < wwa.compute2k().size(); i++)
			System.out.println(wwa.compute2k().get(i));
	}
	
	
}
