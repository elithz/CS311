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
	private int numCrawled; //# of pages that contain topics
	private int pagesRequested;
	private String fileName; 
	
	private static final List<String> NOT_ALLOWED = new ArrayList<String>() {{
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
		numCrawled = 0;
		pagesRequested = 0;
		this.fileName = fileName;
	}
	
	
	
	public class SimpleWikiContentParser {
		private URL page;
		private URL contentPage;
		private String[] searchTerms;
		private boolean containsAllTerms;
		
		/**
		 * Creates a new content parser object.
		 * @param page The start URL page.  This is the full URL, not relative URL.
		 * @param searchTerms Array of keyword terms to search for.
		 */
		public SimpleWikiContentParser(String page, String[] searchTerms) {
			try {
				this.page = new URL(page);
				this.contentPage = urlTransform(this.page);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			this.searchTerms = searchTerms;
			containsAllTerms = false;
			
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(this.contentPage.openStream()));
				String line;
				while((line = br.readLine()) != null) {
					if(containsAll(line)) {
						containsAllTerms = true;
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * After scanning through the text, this will tell if the page contains all terms.
		 * @return true if page contains all terms.  false otherwise.
		 */
		public boolean pageContainsAllTerms() {
			return(containsAllTerms);
		}
		
		/**
		 * Takes a wikipedia page and finds the corresponding raw text page.
		 * Takes the full URL not relative URL.
		 * @param fullURL URL of page.
		 * @return Raw text URL of page.
		 * @throws MalformedURLException If the resulting URL is malformed.
		 */
		private URL urlTransform(URL fullURL) throws MalformedURLException {
			String title = fullURL.getPath().replace("/wiki/", "");
			String path = "/w/index.php?title=" + title + "&action=raw";
			URL url = new URL(fullURL.getProtocol() + "://" + fullURL.getHost() + path);
			
			return(url);
		}
		
		/**
		 * For a line of HTML this will determine if the line contains all search terms. 
		 * @param html Line of HTML to search.
		 * @return true if line contains all search terms.  false otherwise.
		 */
		private boolean containsAll(String html) {
			html = html.toLowerCase();
			html = html.replaceAll("\\p{P}", " ");
			
			for(int i = 0; i < searchTerms.length; i++) {
				if(!html.contains(searchTerms[i].toLowerCase())) return(false);
			}
			
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
					links.addAll(findAllURLs(line));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return links;
	}
	
	
	private List<String> findAllURLs(String html) {
		List<String> urls = new ArrayList<String>();
		int startPos = 0;
		int endPos = 0;
		
		boolean ignoreLine;
		while(startPos >= 0) {
			startPos = html.indexOf("<a href=", startPos);
			if(startPos >= 0) {
				startPos = html.indexOf("\"", startPos) + 1;
				endPos = html.indexOf("\"", startPos);
				
				ignoreLine = false;
				for(String i : NOT_ALLOWED) {
					if(html.substring(startPos, endPos).contains(i)) ignoreLine = true;
				}
				
				if(!ignoreLine) {
					urls.add(html.substring(startPos, endPos));
				}
			}
		} 
		return(urls);
	}
	
	
	
	

	public void crawl()
	{
		// implementation
		SimpleWikiContentParser c = new SimpleWikiContentParser(BASE_URL + seedUrl, topics);
		if(c.pageContainsAllTerms()) {
			toVisit.add(seedUrl);
			pagesRequested++;
			numCrawled++;
		}
		visitedUsefulURL.add(seedUrl);

		//begin crawl
		boolean keepCrawling = true;
		while(keepCrawling) {
			try {
				keepCrawling = crawlNext();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//record results
		try {
			writeToFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean crawlNext() throws InterruptedException {
		if(toVisit.isEmpty()) return(false);
		String fromPage = toVisit.poll();
		
		System.out.println("fromPage: " + BASE_URL + fromPage);
		//SimpleWikiLinkParser s = new SimpleWikiLinkParser(BASE_URL + fromPage);
		//Set<String> newLinks = s.getLinks();
		ArrayList<String> newLinks = this.extractLinks(BASE_URL + fromPage);
		
		pagesRequested++;
		
		SimpleWikiContentParser c;
		for(String l : newLinks) {	
			System.out.println("collected: " + numCrawled + ", Left in Queue: " + toVisit.size() + " toPage: " + BASE_URL + l); //TODO
			
			if(visitedUsefulURL.contains(l) && !fromPage.equalsIgnoreCase(l)) { //previously seen this page and it has keyword matches
				edges.add(fromPage + " " + l); 
			} else if(visitedURL.contains(l) || fromPage.equalsIgnoreCase(l)) { //previously seen this page, no keyword matches
				//do nothing
			} else if(numCrawled < max) { //new unseen page and still need to crawl more pages
				c = new SimpleWikiContentParser(BASE_URL + l, topics);
				if(pagesRequested % 200 == 0) Thread.sleep(2000);
				pagesRequested++;
				
				if(c.pageContainsAllTerms()) {
					toVisit.add(l);
					edges.add(fromPage + " " + l);
					visitedUsefulURL.add(l);
					numCrawled++;
				} else {
					visitedURL.add(l);
				}
			} else { //done getting new pages
				//do nothing
			}
		}
		return(true);		
	}
	
	
	
	private void writeToFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(max + System.lineSeparator());
		
		for(String s : edges) {
			bw.write(s + System.lineSeparator());
		}
		bw.close();
	}
	
	
}



