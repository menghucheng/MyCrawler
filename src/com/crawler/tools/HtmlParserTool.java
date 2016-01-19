package com.crawler.tools;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 处理html标记
 * @author MENGHUCHENG012
 *
 */
public class HtmlParserTool {
		public static Set<String> extracLinks(String url, LinkFilter filter){
			
			Set<String> links = new HashSet<String>();
			
			try {
				Parser parser = new Parser(url);
				parser.setEncoding("gb2312");
				//过滤<frame>标签的filter 用来提取src中的url
				
				NodeFilter frameFilter = new NodeFilter() {
					private static final long serialVersionUID = 1L;

					public boolean accept(Node node) {
						if(node.getText().startsWith("frame src=")){
							return true;
						}else{
							return false;
						}
					}
				};
				
				//OrFilter来设置过滤a 和frame标签
				OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			
				//得到所有经过过滤的标签
				NodeList list = parser.extractAllNodesThatMatch(linkFilter);
				for (int i = 0; i < list.size(); i++) {
					Node tag = list.elementAt(i);
					if(tag instanceof LinkTag){  //a标签
						LinkTag link  = (LinkTag) tag;
						String linkUrl = link.getLink(); //URL
						if (filter.accept(linkUrl)) {
							
						}
					}
				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
