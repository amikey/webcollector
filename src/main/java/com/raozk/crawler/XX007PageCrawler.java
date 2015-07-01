package com.raozk.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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
public class XX007PageCrawler extends AbstractBaseCrawler{

    private static Logger logger = LoggerFactory.getLogger(XX007PageCrawler.class);

    private Site site = Site.me().setDomain("http://www.xx007.cn/");

    private static List<String> startUrls = new LinkedList<String>();


    static {
        startUrls.add("http://www.xx007.cn/");
    }

    public void process(Page page) {
        String url = page.getRequest().getUrl();
        List<String> links = null;
        if(url.indexOf("dispbbs")>0){
            List<Selectable> contents = page.getHtml().xpath("div[@class='post']").nodes();
            for(Selectable div : contents){
                List<Selectable> divs = div.xpath("div/text()").nodes();
                Selectable tail = divs.get(divs.size()-1);
                page.putField("content", tail.get());
            }
        }else if(url.indexOf("index")>0){
            links = page.getHtml().links().regex("http://www\\.xx007\\.cn/.*dispbbs.*").all();
            page.addTargetRequests(links);
        }else{
            links = page.getHtml().links().regex("http://www\\.xx007\\.cn/index\\.asp\\?boardid=\\d+").all();
            page.addTargetRequests(links);
        }
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
