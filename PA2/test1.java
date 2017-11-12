import java.util.ArrayList;

public class test1 {

	public test1(){
		
	}
	public static void main(String[] args){
		ArrayList<String> topics = new ArrayList();
		topics.add("Iowa State");
		topics.add("Cyclones");
		WikiCrawler wc = new WikiCrawler("/wiki/Iowa_State_University", 100, topics, "WikiISU.txt");
		wc.crawl();
	}
}
