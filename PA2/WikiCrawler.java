// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)


/**	
*	@Filename		WikiCrawler.java
*	@Description	WikiCrawler of PA2
*	@Version		1.0
*	@Created		11.10.2017 15h00min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
	// other member fields and methods

	private LinkedList<String> toVisit; //link queue of vertices need to be visited
	private Set<String> visitedURL; //visited URLs witch don't contain topics
	private Set<String> visitedUsefulURL; //visited URLs which contain topics
	private List<String> edges;
	
	private String seedUrl;
	private String[] topics;
	private int max;
	private int crawled; //# of pages that contain topics
	private int pagesReq;
	private String fileName; 
	
	//ignorelist filter
	private static final List<String> IGNORE_LIST = new ArrayList<String>() {{
		add("trap"); add("/wiki/Special");
		add("/wiki/Wikipedia:Articles_for_deletion");
		add("/wiki/Wikipedia:Votes_for_deletion");
		add("/wiki/Wikipedia:Pages_for_deletion");
		add("/wiki/Wikipedia:Miscellany_for_deletion");
		add("/wiki/Wikipedia:Miscellaneous_deletion");
		add("/wiki/Wikipedia:Copyright_problems");
		add("/wiki/Wikipedia:Protected_titles");
		add("/wiki/Wikipedia:WikiProject_Spam");
		add("/wiki/MediaWiki:Spam-blacklist");
		add("/wiki/MediaWiki_talk:Spam-blacklist");
		add("/wiki/Portal:Prepared_stories");
		add("/wiki/Wikibooks:Votes_for_deletion");
		add("/wiki/Wikipedia:Requests_for_arbitration");
		add("redlink=1;");
		add("/wiki/Main_Page");
		add(".org"); add(".net"); add(".com");
		add("#"); add(":"); add("&");
	}};
	
	
	
	
	
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		// implementation
		toVisit = new LinkedList<String>();
		visitedURL = new HashSet<String>();
		visitedUsefulURL = new HashSet<String>();
		edges = new LinkedList<String>();
		
		this.seedUrl = seedUrl;
		this.topics = (String[]) topics.toArray(new String[topics.size()]);
		this.max = max;
		crawled = 0;
		pagesReq = 0;
		this.fileName = fileName;
	}
	
	
	
	public class pageParser {
		private URL page;
		private URL contentPage;
		private String[] searchTopics;
		private boolean containTopics;
		
		public pageParser(String page, String[] searchTopics) {
			try {
				this.page = new URL(page);
				this.contentPage = urlTransform(this.page);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			this.searchTopics = searchTopics;
			containTopics = false;
			
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(this.contentPage.openStream()));
				String line;
				while((line = br.readLine()) != null) {
					if(containsAll(line)) {
						containTopics = true;
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public boolean pageContainTopics() {
			return(containTopics);
		}
		
		private URL urlTransform(URL fullURL) throws MalformedURLException {
			String title = fullURL.getPath().replace("/wiki/", "");
			String path = "/w/index.php?title=" + title + "&action=raw";
			URL url = new URL(fullURL.getProtocol() + "://" + fullURL.getHost() + path);
			
			return(url);
		}
		
		private boolean containsAll(String htmlstr) {
			htmlstr = htmlstr.toLowerCase();
			htmlstr = htmlstr.replaceAll("\\p{P}", " ");
			
			for(int i = 0; i < searchTopics.length; i++)
				if(!htmlstr.contains(searchTopics[i].toLowerCase()))
					return(false);
			
			return(true);
		}
	}
	
	

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc)
	{
		// implementation
		URL page = null;
		ArrayList<String> links;
		try {
			page = new URL(doc);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		links = new ArrayList<String>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(page.openStream()));
			String line;
			boolean startRead = false;
			while((line = br.readLine()) != null) {		
				if(line.contains("<p>")) {
					startRead = true;
				}
				if(startRead && line.contains("<a href=") ) {
					links.addAll(getURLs(line));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return links;
	}
	
	
	private List<String> getURLs(String htmlstr) {
		List<String> urls = new ArrayList<String>();
		int startPt = 0;
		int endPt = 0;
		
		boolean passLine;
		while(startPt >= 0) {
			startPt = htmlstr.indexOf("<a href=", startPt);
			if(startPt >= 0) {
				startPt = htmlstr.indexOf("\"", startPt) + 1;
				endPt = htmlstr.indexOf("\"", startPt);
				
				passLine = false;
				for(String i : IGNORE_LIST) {
					if(htmlstr.substring(startPt, endPt).contains(i)) passLine = true;
				}
				
				if(!passLine) {
					urls.add(htmlstr.substring(startPt, endPt));
				}
			}
		} 
		return(urls);
	}
	
	
	
	

	public void crawl()
	{
		// implementation
		pageParser c = new pageParser(BASE_URL + seedUrl, topics);
		if(c.pageContainTopics()) {
			toVisit.add(seedUrl);
			pagesReq++;
			crawled++;
		}
		visitedUsefulURL.add(seedUrl);

		//start crawl
		boolean keepCrawling = true;
		while(keepCrawling) {
			try {
				keepCrawling = nextCrawl();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//write results to assigned file
		try {
			writeToFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean nextCrawl() throws InterruptedException {
		if(toVisit.isEmpty()) return(false);
		String fromPage = toVisit.poll();
		
		System.out.println("fromPage: " + BASE_URL + fromPage);
		ArrayList<String> newLinks = this.extractLinks(BASE_URL + fromPage);
		
		pagesReq++;
		
		pageParser c;
		for(String l : newLinks) {	
			System.out.println("got: " + crawled + ", in Q: " + toVisit.size() + " toPage: " + BASE_URL + l); //TODO
			 
			if(visitedUsefulURL.contains(l) && !fromPage.equalsIgnoreCase(l)) 
				//have seen the page and contains topics
				edges.add(fromPage + " " + l); 
			else if(visitedURL.contains(l) || fromPage.equalsIgnoreCase(l)) { 
				//have seen the page but no topics in it
				//do nothing
			} else if(crawled < max) { 
				//new page, need to keep crawling
				c = new pageParser(BASE_URL + l, topics);
				if(pagesReq % 200 == 0) 
					Thread.sleep(2000);
				pagesReq++;
				
				if(c.pageContainTopics()) {
					toVisit.add(l);
					edges.add(fromPage + " " + l);
					visitedUsefulURL.add(l);
					crawled++;
				} else 
					visitedURL.add(l);
				
			} else { 
				//done getting new pages
				//do nothing
			}
		}
		return(true);		
	}
	
	
	
	private void writeToFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(max + System.lineSeparator());
		
		for(String s : edges)
			bw.write(s + System.lineSeparator());
		bw.close();
	}
	
	
}



