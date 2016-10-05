package com.webspider;

import java.util.*;

public class Spider {

	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();

	// make sure that the crawled web page are not visited again
	private String nextURL() {

		String url;
		// Search the next url until the unique url is found
		do {
			url = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(url));
		this.pagesVisited.add(url);
		return url;

	}

	public void search(String url, String searchWord) {

		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {

			String currentUrl;
			SpiderLeg L = new SpiderLeg();

			if (this.pagesVisited.isEmpty()) {

				currentUrl = url;
				this.pagesVisited.add(currentUrl);

			} else {
				currentUrl = this.nextURL();
			}

			L.crawl(url);
			boolean success = L.searchForWord(searchWord);
			if (success) {
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
			}
			this.pagesToVisit.addAll(L.getLinks());
		}

		  System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
	}

}
