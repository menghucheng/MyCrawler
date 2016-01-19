package com.crawler.test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;



public class Spider {

	private static HttpClient httpClient = new HttpClient();
	
	private static Logger LOG = Logger.getLogger("Spider");
	
	public static boolean downLoadPage(String path) throws Exception{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//得到post方法
		GetMethod getMethod = new GetMethod(path);
		//执行，返回状态码
		int statusCode = httpClient.executeMethod(getMethod);
		if(statusCode != HttpStatus.SC_OK){
			return false;
		}
		
		inputStream = getMethod.getResponseBodyAsStream();
		
		//按照url命名
		String fileName = "D://" + path.substring(path.indexOf("/") + 1) + ".html";
		
		outputStream = new FileOutputStream(fileName);
		//输出到文件
		int tempByte = -1;
		while((tempByte = inputStream.read()) != -1){
			outputStream.write(tempByte);
		}
		
		//关闭流
		if(inputStream != null){
			inputStream.close();
		}
		
		if(outputStream != null){
			outputStream.close();
		}
		
		LOG.info("downLoad successful!");
		
		return true;
	}
	
	public static void main(String[] args){
		try{
			//抓取百度首页
			downLoadPage("http://www.baidu.com");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
