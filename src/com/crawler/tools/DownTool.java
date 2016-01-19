package com.crawler.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownTool {
	
	private static final Logger LOG = Logger.getLogger(DownTool.class.getName());
	
	/**
	 * 根据Url和网页类型生成需要保存的网页的文件名，去除URL中的文件名中的非法字符
	 * @param url
	 * @param contentType
	 * @return
	 */
	private String getFileNameByUrl(String url, String contentType){
		//移除”http://“ 一共七个字符
		url = url.substring(7);
		
		//确认抓取到的页面为text/html类型
		if(contentType.indexOf("html") != -1){
			//把所有的特殊符号转化为下划线,文件命名的需要
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
		}else{
			url = url.replace("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/")+1);
		}
		return url;
	}
	
	/**
	 * 保存网页字节数组到本地文件，filePath为要保存的文件的相对地址
	 * @param data
	 * @param filePath
	 */
	private void saveToLocal(byte[] data, String filePath){
		try{
			DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(new File(filePath)));
			
			for (int i = 0; i < data.length; i++) {
				outputStream.write(data[i]);
			}
			outputStream.flush();
			outputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String downloadFile(String url){
	String filePath = null;
		
		//生成HttpClient对象并设置参数
		HttpClient httpClient = new HttpClient();
		
		//设置Http连接超时 5s
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		//生成getMethod对象并设置参数
		GetMethod getMethod = new GetMethod(url);
		
		//设置get请求超时 5s
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		
		//设置 重试处理
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		//执行GET请求
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			
			//判断访问的状态码
			if(statusCode != HttpStatus.SC_OK){
				LOG.info( "Method Fialed: " + getMethod.getStatusLine());
				filePath = null;
			}
			
			//处理HTTP响应内容
			byte[] responseBody = getMethod.getResponseBody();
			
			//根据网页url生成保存时的文件名
			filePath = "temp\\" + getFileNameByUrl(url, getMethod.getResponseHeader("Conten-Type").getValue());
			saveToLocal(responseBody, filePath);
		} catch (HttpException e) {
			LOG.info("请检查你得http地址是否正确");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.info("发生网络异常");
			e.printStackTrace();
		}
	
		return filePath;
	}
	
	public static void main(String[] args) {
		DownTool dt = new DownTool();
		System.out.println(dt.getFileNameByUrl("http://www.baidu.com", "text/html"));
	}
	
}
