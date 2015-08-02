package com.raozk.jiangsu;

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
public class JSZaiGuaPaiGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(JSZaiGuaPaiGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://ybk.jscaee.com.cn/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "04";
    private static String type = "3";


    static {
        startUrls.add("http://ybk.jscaee.com.cn/announcement/re-listing/");
    }

    public void process(Page page) {//http://ybk.jscaee.com.cn/announcement/trusteeship/index_p3.html
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("//ul[@class='page-list']/").links().all());
        //http://ybk.jscaee.com.cn/announcement/trusteeship/2015-06/23/0623O42015.html
        List<String> links = page.getHtml().xpath("//ul[@class='link-list']/").links().all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(band, type, link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//div[@class='title']/h2/text()").get();
        String content = page.getHtml().xpath("//div[@class='content']/html()").get();
        String time = page.getHtml().xpath("//div[@class='info']").get();
        if(StringUtils.hasText(time)){
            time = time.substring(time.indexOf("<small>时间:</small>")+"<small>时间:</small>".length(), time.indexOf("<small>来源:</small>")).trim();
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            //2015-7-8 21:55
            //page.putField("announcement", new Announcement(title, content, band, type, time));
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time);
            } catch (ParseException e) {
                logger.error("parse time error", e);
                try {
                    timeDate = new SimpleDateFormat("yyyy/MM/dd").parse(time);
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
