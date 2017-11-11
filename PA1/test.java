
public class test {

	public test(){
		
	}
	public static void main(String[] args){
		String[] str = {"V","A","A","B","B","B","C","E"};
		BinaryST bst = new BinaryST(str);
		System.out.println(bst.size());
		System.out.println(bst.frequency("A"));
		System.out.println(bst.height());
		System.out.println(bst.rankOf("V"));
		System.out.println(bst.distinctSize());
//		System.out.println(bst.inOrder()[0]);
//		System.out.println(bst.inOrder()[1]);
//		System.out.println(bst.inOrder()[2]);
//		System.out.println(bst.inOrder()[3]);
//		System.out.println(bst.inOrder()[4]);
//		System.out.println(bst.inOrder()[5]);
//		System.out.println(bst.inOrder()[6]);
//		System.out.println(bst.inOrder()[7]);
		bst.add("F");
		bst.remove("B");
		System.out.println(bst.frequency("B"));
		System.out.println(bst.size());
		System.out.println(bst.height());
		System.out.println(bst.frequency("F"));
		System.out.println(bst.frequency("G"));
		System.out.println(bst.rankOf("G"));
		System.out.println(bst.inOrder()[0]);
		System.out.println(bst.inOrder()[1]);
		System.out.println(bst.inOrder()[2]);
		System.out.println(bst.inOrder()[3]);
		System.out.println(bst.inOrder()[4]);
		System.out.println(bst.inOrder()[5]);
		System.out.println(bst.inOrder()[6]);
		System.out.println(bst.inOrder()[7]);
		System.out.println("");
		System.out.println(bst.preOrder()[0]);
		System.out.println(bst.preOrder()[1]);
		System.out.println(bst.preOrder()[2]);
		System.out.println(bst.preOrder()[3]);
		System.out.println(bst.preOrder()[4]);
		System.out.println(bst.preOrder()[5]);
		System.out.println(bst.preOrder()[6]);
		System.out.println(bst.preOrder()[7]);
		System.out.println(bst.distinctSize());
	}
}
