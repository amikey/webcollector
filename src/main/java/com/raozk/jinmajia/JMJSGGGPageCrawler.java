package com.raozk.jinmajia;

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
public class JMJSGGGPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(JMJSGGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "03";
    private static String type = "申购公告";

    static {
        startUrls.add("http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/sggp/");
    }

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://qbyp\\.jinmajia\\.com/article/mtbd/qbyp/gggs/sggp/index\\.shtml\\?\\d+").all());
        List<String> links = page.getHtml().links().regex("http://qbyp\\.jinmajia\\.com/article/mtbd/qbyp/gggs/sggp/\\d+/\\d+.shtml").all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//h2[@class='title']/text()").get();
        String content = page.getHtml().xpath("//td[@class='content']/html()").get();
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            String[] ts = page.getHtml().get().split("var tm='");
            if(ts.length>1){
                String time = ts[1];
                time = time.substring(0, time.indexOf("'"));
                if(StringUtils.hasText(time)){
                    page.putField("announcement", new Announcement(title, content, band, type, time));
                    logger.info("crawed:" + title);
                }
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
