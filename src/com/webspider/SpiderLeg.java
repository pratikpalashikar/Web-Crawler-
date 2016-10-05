package com.webspider;

import java.io.IOException;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>(); // Just a list of
															// URLs
	private Document htmlDocument; // This is our web page, or in other words,
									// our document

	// This class provides the public method

	public void crawl(String nextURL) {
		// Give it a URL and it makes an HTTP request for a web page

		try {
			Connection connection = Jsoup.connect(nextURL).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			System.out.println("Received web page at " + nextURL);
			Elements linksOnPage = htmlDocument.select("a[href]");
			for (Element link : linksOnPage) {
				this.links.add(link.absUrl("href"));
			}

			System.out.println("Found (" + linksOnPage.size() + ") links");
		} catch (IOException ioe) {
			System.out.println("Error in out HTTP request " + ioe);
		}

		return;
	}

	
	public List<String> getLinks() 
	{
	  return this.links;
	}
	
	public boolean searchForWord(String word) {
		// Tries to find a word on the page
		System.out.println("Searching for the word " + word + "...");
		String bodyText = this.htmlDocument.body().text();
		return bodyText.toLowerCase().contains(word.toLowerCase());

	}

	
}
