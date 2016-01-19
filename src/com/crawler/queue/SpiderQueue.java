package com.crawler.queue;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MENGHUCHENG012
 *	url
 */
public class SpiderQueue {
	
	//访问过的urls
	private static Set<Object> visitedUrls = new HashSet<Object>();
	
	//未访问的urls
	private static Queue unVisitedUrls= new Queue();
	
	
	/**
	 * 添加
	*/
	public static void addVisitedUrl(String url){
		visitedUrls.add(url);
	}
	
	/**
	 * 移除
	 * @param url
	 */
	public static void removeVisitedUrl(String url){
		visitedUrls.remove(url);
	}
	
	/**
	 * 获取访问过的url数目
	 * @return
	 */
	public static int getVisitedUrlNum(){
		return visitedUrls.size();
	}
	/**
	 * 获取待访问的urls
	 * @return
	 */
	public static Queue getUnvisitedUrls(){
		return unVisitedUrls;
	}
	/**
	 * 未访问的url出队列 并返回url
	 * @return
	 */
	public static Object unVisitedUrlDeQueue(){
		return unVisitedUrls.deQueue();
	}
	
	/**
	 * 添加到待访问队列中
	 * @param url
	 */
	public static void addUnvisitedUrl(String url){
		if(url != null && "".equals(url.trim()) && !visitedUrls.contains(url)
				&& !unVisitedUrls.contains(url)){
			unVisitedUrls.enQueue(url);
		}
	}
	
	public static boolean unVisitedUrlsEmpty(){
		return unVisitedUrls.isEmpty();
	}
	
}
