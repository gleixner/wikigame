package com.wikigame.wikigame;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class GameTask implements Runnable {
	
	private Page taskData;
	private String taskTarget;
	private Set<String> visitedUrls;
	
	public GameTask(Page taskData, Set<String> visitedUrls, String taskTarget) {
		this.taskData = taskData;
		this.taskTarget = taskTarget;
		this.visitedUrls = visitedUrls;
	}

	@Override
	public void run() {
		App.startTask();
		List<String> currentChildren = taskData.getChildUrls();
		System.out.println("Analyzing page " + taskData.getUrl() + " with " + currentChildren.size() + " children");
		for(String url : currentChildren) {
			if(taskTarget.equals(url)) {
				System.out.println("Success!");
				App.success(taskData, taskTarget);
				break;
			} else if (visitedUrls.add(url)){
				App.addTask(new GameTask(new Page("https://en.wikipedia.org" + url, taskData), visitedUrls, taskTarget));
			}
		}
		App.completeTask();
	}

	public String getUrl() {
		return taskData.getUrl();
	}
}
