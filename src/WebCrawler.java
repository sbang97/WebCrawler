/*
 * Simon Bang
 * 1/29/2017
 * 
 * This program takes in a link to a web page and a word 
 * and collects all of the words on the given web page. It also
 * returns all of the links on the web page. If the given word is
 * not found on the page, it moves on to the next link
 */
import java.util.*;

public class WebCrawler {
	private static final int MAX_PAGES = 20;
	private Set<String> urlsVisited;
	private List<String> urlsNotVisited;
	
	// creates a new empty WebCrawler
	public WebCrawler() {
		urlsVisited = new HashSet<String>();
		urlsNotVisited = new LinkedList<String>();
	}
	
	/**
	 * @param url a URL to a webpage that it will parse
	 * @param word a word that will the web crawler will search each page for
	 */
	public void search(String url, String word) {
		while (this.urlsVisited.size() < MAX_PAGES) {
			String currURL;
			CrawlerLeg branch = new CrawlerLeg();
			if (this.urlsNotVisited.isEmpty()) {
				currURL = url;
				this.urlsVisited.add(url);
			} else {
				currURL = this.nextURL();
			}
			branch.crawl(currURL);
			boolean worked = branch.wordSearch(word);
			if (worked) {
				System.out.println(String.format("**Success** Word %s found at %s", word, currURL));
				break;
			}
		this.urlsNotVisited.addAll(branch.getLinks());	
		}
	System.out.println("\n**Done** Visited " + this.urlsVisited.size() + " total web page(s)");
	}
	
	// Returns the next link that has not been visited
	private String nextURL() {
		String nextURL;
		do {
			nextURL = this.urlsNotVisited.remove(0);
		} while (this.urlsVisited.contains(nextURL));
		this.urlsVisited.add(nextURL);
		return nextURL;
	}
	
	
}