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
public class JMJTGGGPageCrawler extends AbstractBaseCrawler{

    private static Logger logger = LoggerFactory.getLogger(JMJTGGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://qbyp.jinmajia.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "金马甲";
    private static String type = "托管公告";

    static {
        startUrls.add("http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/rksq/");
    }

    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://qbyp\\.jinmajia\\.com/article/mtbd/qbyp/gggs/rksq/\\d+/\\d+.shtml").all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//h2[@class='title']/text()").get();
        String context = page.getHtml().xpath("//td[@class='content']/html()").get();
        if(StringUtils.hasText(title)&&StringUtils.hasText(context)) {
            String[] ts = page.getHtml().get().split("var tm='");
            if(ts.length>1){
                String time = ts[1];
                time = time.substring(0, time.indexOf("'"));
                if(StringUtils.hasText(time)){
                    page.putField("time", time);
                }
            }
            page.putField("title", title);
            page.putField("context", context);
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
