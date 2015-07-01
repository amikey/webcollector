package com.raozk.crawler;

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
public class JMJTZGGPageCrawler extends AbstractBaseCrawler{

    private static Logger logger = LoggerFactory.getLogger(JMJTZGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "金马甲";
    private static String type = "通知公告";

    static {
        startUrls.add("http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/pttz/");
    }

    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://qbyp\\.jinmajia\\.com/article/mtbd/qbyp/gggs/pttz/\\d+/\\d+.shtml").all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
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
            page.putField("type", type);
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
