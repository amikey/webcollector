package com.raozk.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class XianjiaPageCrawler extends AbstractBaseCrawler{

    private static Logger logger = LoggerFactory.getLogger(XianjiaPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.ybk001.com/");

    private static List<String> startUrls = new LinkedList<String>();

    static {
        startUrls.add("http://www.ybk001.com/asp/stamppriceurl.asp");
    }

    public void process(Page page) {
        String html = page.getHtml().xpath("//div[@class='editor']").get();

        String[] contents = html.split("\n");
        for(String s : contents){
            int index = s.indexOf("元成交");
            if(index>0){
                String xianjia = s.substring(0,index);
                xianjia = xianjia.substring(xianjia.lastIndexOf(">")+1, xianjia.length());
                String name = "";
                double price = 0;
                for(int i =xianjia.length()-1; i > 1; i--){
                    try{

                        price = NumberUtils.parseNumber(xianjia.substring(i, xianjia.length()), Double.class);
                        name = xianjia.substring(0, i);
                    }catch (IllegalArgumentException e){
                        i = 0;
                    }
                }
                //System.out.println(String.format("%s , %f", name, price));
            }

        }

    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
