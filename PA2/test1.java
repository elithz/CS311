import java.util.ArrayList;

public class test1 {

	public test1(){
		
	}
	public static void main(String[] args){
		ArrayList<String> topics = new ArrayList();
		WikiCrawler wc = new WikiCrawler("/wiki/Computer_Science", 200, topics, "WikiCS.txt");
		wc.crawl();
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		System.out.println(gp.diameter());
		System.out.println(gp.numVertices);
		System.out.println(gp.graph.size());
		//System.out.println(gp.graph.values().toString());
		System.out.println(gp.bfsPath("/wiki/Computer_Science", "/wiki/Computational_complexity_theory"));
		System.out.println(gp.outDegree("/wiki/Procedure_(computer_science)"));
		System.out.println(gp.centrality("/wiki/Computational_complexity_theory"));
	}
}
