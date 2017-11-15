import java.util.ArrayList;

public class test1 {

	public test1(){
		
	}
	public static void main(String[] args){
		ArrayList<String> topics = new ArrayList();
		WikiCrawler wc = new WikiCrawler("/wiki/Computer_Science", 200, topics, "WikiCS.txt");
		wc.crawl();
	}
}
