package com.raozk.zgyjs;

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
public class ZYZhongXinGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(ZYZhongXinGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.ttybk.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "05";
    private static String type = "3";

    static {
        startUrls.add("http://www.ttybk.com/zgyjs/list.asp?id=2");
    }

    public void process(Page page) {
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("//div[@class='st_rightbor']/div[@class='page']").links().all());
        List<String> links = page.getHtml().xpath("//div[@class='st_rightbor']/div[@class='list']").links().all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(band, type, link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//div[@class='newstitle']/text()").get();
        String content = page.getHtml().xpath("//div[@class='news_w']/html()").get();
        String time = page.getHtml().xpath("//div[@class='newstime']/text()").get();
        if(StringUtils.hasText(time)){
            time = time.split("时间：")[1].trim();
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            //2015/7/8 21:55:53
            //page.putField("announcement", new Announcement(title, content, band, type, time));
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                //logger.error("parse time error", e);
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
