package com.raozk.zhongnan;

import com.raozk.crawler.AbstractBaseCrawler;
import com.raozk.modole.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class ZNXianHuoCanKaoJiaPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(ZNXianHuoCanKaoJiaPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.znypjy.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "02";
    private static String type = "3";


    static {
        startUrls.add("http://www.znypjy.com/a/xinxipilu/xianhuocankaojia/");
    }

    public void process(Page page) {
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("//ul[@class='pagelist']").links().all());
        List<String> links = page.getHtml().xpath("//div[@class='main_right_list']").links().all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(band, type, link)) {
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
            try {
                page.putField("announcement", new Announcement(title, content, band, type, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time)));
            } catch (ParseException e) {
                logger.error("get time error", e);
            }
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
