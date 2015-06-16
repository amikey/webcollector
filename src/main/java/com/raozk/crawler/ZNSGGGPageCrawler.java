package com.raozk.crawler;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class ZNSGGGPageCrawler implements BaseCrawler{

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    static {
        startUrls.add("http://www.znypjy.com/a/xinxipilu/shengougonggao/");
    }

    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://www\\.znypjy\\.com/a/xinxipilu/shengougonggao/\\d+/\\d+/\\d+.html").all();
        page.addTargetRequests(links);
        page.putField("title",page.getHtml().xpath("//p[@class='article_title']/text()").all());
        page.putField("content",page.getHtml().xpath("//div[@class='article_content']/html()").all());
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
