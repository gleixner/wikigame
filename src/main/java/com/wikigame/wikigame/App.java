package com.wikigame.wikigame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
//        System.out.println( "Hello World!" );
//        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Kevin_Bacon").get();
//        Elements elms = doc.select("#mw-content-text a[href^=\"/wiki/\"]");
//        System.out.println(elms.first());
//        String title = doc.title();
//        System.out.println(title);
    	
//    	String start = "https://en.wikipedia.org/wiki/Fenestrelle_Fort";
    	String start = "https://en.wikipedia.org/wiki/Phalonidia_hapalobursa";
//    	String start = "https://en.wikipedia.org/wiki/Mystic_River_(film)";
//    	String start = "https://en.wikipedia.org/wiki/76th_Academy_Awards";
//    	String start = "https://en.wikipedia.org/wiki/Michael_Caine";

    	String end = "/wiki/Kevin_Bacon";
    	findPath(new Page(start, null), end);
    }
    
    private static String findPath(Page page, String target) throws IOException {
    	Queue<Page> q = new ConcurrentLinkedQueue<>();
    	Set<String> visitedUrls = new HashSet<>();
    	boolean loop = true;
    	int count = 0;
    	
    	q.offer(page);
    	while(q.peek() != null && loop) {
    		System.out.println("Backlog size is " + q.size());
    		Page current = q.poll();
    		List<String> currentChildren = current.getChildUrls();
    		System.out.println("Analyzing page " + current.getUrl() + " with " + currentChildren.size() + " children");
    		for(String url : currentChildren) {
    			if(target.equals(url)) {
    				System.out.println("Success!");
    				success(current, target);
    				loop = false;
    				break;
    			} else if (visitedUrls.add(url)){
    				q.offer(new Page("https://en.wikipedia.org" + url, current));
    			}
    		}
    		System.out.println("Searched " + ++count + " pages");
    	}	
		return null;
    }

	private static void success(Page winner, String target) {
		System.out.println(winner.getHistory() + " --> " + target);
	}
}
