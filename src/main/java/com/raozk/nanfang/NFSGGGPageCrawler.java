package com.raozk.nanfang;

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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class NFSGGGPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(NFSGGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.nfqbyp.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "06";
    private static String type = "申购公告";


    static {
        startUrls.add("http://www.nfqbyp.com/infomation.html?newsTypeID=17509&newsType=%E7%94%B3%E8%B4%AD%E6%8C%82%E7%89%8C");
    }

    public void process(Page page) {//http://www.nfqbyp.com/infomation.html?pageIndex=2&newsTypeID=16793&newsType=%E4%B8%AD%E5%BF%83%E9%80%9A%E5%91%8A
        page.addTargetRequests(page.getHtml().xpath("div[@class='page_num']").links().all());
        List<String> links = page.getHtml().xpath("ul[@class='list_news']").links().all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
           }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//div[@class='dnews_title']/text()").get();
        String content = page.getHtml().xpath("//div[@class='dnews_content']/html()").get();
        String time = page.getHtml().xpath("//div[@class='dnews_info']/text()").get();
        if(StringUtils.hasText(time)){
            time = time.substring(0,19).trim();
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            //2015/7/8 21:55:53
            //page.putField("announcement", new Announcement(title, content, band, type, time));
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                logger.error("parse time error", e);
                try {
                    timeDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
                } catch (ParseException e1) {
                    timeDate = new Date();
                }
            }
            page.putField("announcement", new Announcement(title, content, band, type, timeDate));
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