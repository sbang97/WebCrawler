/*
 * Simon Bang
 * This is a class handles the HTTP requests, going through the HTML
 * text and collecting links that contain the search word.
 */
import java.io.IOException;
import java.util.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerLeg {
	private static final String USER_AGENT = 
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	private List<String> links = new LinkedList<String>();
	private Document pageHTML;
	
	/**
	 * @param url that will be 
	 * @return
	 */
	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			this.pageHTML = connection.get();
			// 200 is the HTTP status code that means Good
			if (connection.response().statusCode() == 200) {
				System.out.println("Checking the website URL:" + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("Failed to retrieve HTML data from the given URL");
				return false;
			}
			Elements linksFound = pageHTML.select("a[href]");
			System.out.println("Found " + linksFound.size() + " links");
			for (Element link : linksFound) {
				this.links.add(link.absUrl("href"));
			}
			// if the HTTP request is successful
			return true;
		}
		catch (IOException ioe) {
			// If the HTTP request somehow fails
			return false;
		}
	}
	
	/**
	 * @param word that will be searched for in the webpage
	 * @return True if the word is found, False if not
	 */
	public boolean wordSearch(String word) {
		if (this.pageHTML == null) {
			System.out.println("FAILURE, call the crawl() method before you call this method");
			return false;
		}
		word = word.toLowerCase();
		System.out.println("Currently searching for the word:" + word);
		String htmlBody = this.pageHTML.body().text();
		return htmlBody.toLowerCase().contains(word);
	}
	
	// Returns all of the links found on the webpage
	public List<String> getLinks() {
		return this.links;
	}
}