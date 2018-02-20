package com.wikigame.wikigame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class App  {
	
	private static BlockingQueue<Runnable> qu = new LinkedBlockingQueue<>();
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 100, TimeUnit.MILLISECONDS, qu);
	private static Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
	private static AtomicInteger addedTasks = new AtomicInteger(0);
	private static AtomicInteger executedTasks = new AtomicInteger(0);
	private static AtomicInteger completedTasks = new AtomicInteger(0);
	private static long startTime;
	
	
    public static void main( String[] args ) throws IOException {
    	String start = "https://en.wikipedia.org/wiki/Fenestrelle_Fort";
//    	String start = "https://en.wikipedia.org/wiki/Phalonidia_hapalobursa";
//    	String start = "https://en.wikipedia.org/wiki/Mystic_River_(film)";
//    	String start = "https://en.wikipedia.org/wiki/76th_Academy_Awards";
//    	String start = "https://en.wikipedia.org/wiki/Michael_Caine";
//    	String start = "https://en.wikipedia.org/wiki/Hollywood_Walk_of_Fame";

    	String end = "/wiki/Kevin_Bacon";
    	startTime = System.currentTimeMillis();
    	executor.execute(new GameTask(new Page(start, null), visitedUrls, end));
    }

	public  static void success(Page winner, String target) {
		synchronized (qu) {
			executor.shutdownNow();
			System.out.println("Time elapsed is " + (System.currentTimeMillis() - startTime));
			System.out.println(winner.getHistory() + " --> " + target);
			System.out.println(executedTasks.get() + " tasks were started");
			System.out.println(completedTasks.get() + " tasks were finished");
			System.out.println(addedTasks.get() + " tasks were scheduled to run");
		}
	}
	
	public static void addTask(GameTask task) {
		addedTasks.incrementAndGet();
		try {
			executor.execute(task);
		} catch(RejectedExecutionException e) {
		}
	}
	
	public static void startTask() {
		executedTasks.incrementAndGet();
	}
	
	public static void completeTask() {
		completedTasks.incrementAndGet();
	}
}
