package com.raozk.crawler;

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
public class ZNTZGGPageCrawler extends AbstractBaseCrawler{

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "中南邮票交易所";

    static {
        startUrls.add("http://www.znypjy.com/a/xinxipilu/tongzhigonggao/list_7_1.html");
    }

    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://www\\.znypjy\\.com/a/xinxipilu/tongzhigonggao/\\d+/\\d+/\\d+.html").all();
        for(String link : links) {
            if (!crawed(link)) {
                page.addTargetRequests(links);
            }
        }
        String title = page.getHtml().xpath("//p[@class='article_title']/text()").get();
        String context = page.getHtml().xpath("//div[@class='article_content']/html()").get();
        String time = page.getHtml().xpath("//p[@class='article_note']/text()").get();
        if(StringUtils.hasText(time)){
            time = time.substring(0,16);
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(context)) {
            page.putField("title", title);
            page.putField("context", context);
            page.putField("time", time);
            page.putField("band", band);
        }
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
