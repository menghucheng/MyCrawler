package com.crawler.queue;

import java.util.LinkedList;

/**
 * @author MENGHUCHENG012
 *	定义url队列
 */
public class Queue {
	
	private LinkedList<Object> queue = new LinkedList<Object>();
	
	/**
	 * 加入队列
	 * @param obj
	 */
	public void enQueue(Object obj){
		queue.add(obj);
	}
	
	/**
	 * 移除队列中第一项并且返回 
	 * @return
	 */
	public Object deQueue(){
		return queue.removeFirst();
	}
	
	/**
	 * 判空
	 * @return
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	/**
	 * 判断是否包含某个元素
	 * @param obj
	 * @return
	 */
	public boolean contains(Object obj){
		return queue.contains(obj);
	}
	
	
	
}
