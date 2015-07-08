package com.raozk.zhongnan;

import com.raozk.crawler.AbstractBaseCrawler;
import com.raozk.modole.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class ZNTZGGPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(ZNTZGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "02";
    private static String type = "通知公告";


    static {
        startUrls.add("http://www.znypjy.com/a/xinxipilu/tongzhigonggao/list_7_1.html");
    }

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://www\\.znypjy\\.com/a/xinxipilu/tongzhigonggao/list_\\d+_\\d+.html").all());
        List<String> links = page.getHtml().links().regex("http://www\\.znypjy\\.com/a/xinxipilu/tongzhigonggao/\\d+/\\d+/\\d+.html").all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//p[@class='article_title']/text()").get();
        String content = page.getHtml().xpath("//div[@class='article_content']/html()").get();
        String time = page.getHtml().xpath("//p[@class='article_note']/text()").get();
        if(StringUtils.hasText(time)){
            time = time.substring(0,16);
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            page.putField("announcement", new Announcement(title, content, band, type, time));
            logger.info("crawed:"+title);
        }
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}