package com.wikigame.wikigame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {
	
	private ArrayList<String> childUrls = new ArrayList<>();
	private String url;
	private String history = "";
	
	public Page(String selfUrl, Page parentNode) throws IOException {
		url = selfUrl;
		if(parentNode != null) {
			history = parentNode.getHistory() + " --> " + selfUrl;
		} else {
			history = selfUrl;
		}
	}
	
	private void init() {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
	        Elements elms = doc.select("#mw-content-text a[href^=\"/wiki/\"]");
			for(Element elm : elms) {
				if(elm.attr("href").startsWith("/wiki/") 
						&& !elm.attr("href").startsWith("/wiki/File")
						&& !elm.attr("href").startsWith("/wiki/Special")
						&& !elm.attr("href").startsWith("/wiki/Help")) {
					childUrls.add(elm.attr("href"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public List<String> getChildUrls() {
		if(childUrls.size() == 0) {
			init();
		}
		return childUrls;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String toString() {
		return getUrl();
	}
	
	public String getHistory() {
		return history;
	}
}
