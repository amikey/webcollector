package com.raozk.nanfang;

import com.raozk.crawler.AbstractBaseCrawler;
import com.raozk.modole.AdvisoryNews;
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
public class NFZXPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(NFZXPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.nfqbyp.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "06";
    private static String type = "资讯";


    static {
        startUrls.add("http://www.nfqbyp.com/news.html?newsTypeID=16800&newsType=%E8%A1%8C%E4%B8%9A%E5%8A%A8%E6%80%81");
        startUrls.add("http://www.nfqbyp.com/news.html?newsTypeID=16801&newsType=%E5%B8%82%E5%9C%BA%E7%82%B9%E8%AF%84");
        startUrls.add("http://www.nfqbyp.com/news.html?newsTypeID=16802&newsType=%E8%97%8F%E5%93%81%E5%88%86%E6%9E%90");
        startUrls.add("http://www.nfqbyp.com/news.html?newsTypeID=16803&newsType=%E5%90%8D%E5%AE%B6%E4%B8%93%E6%A0%8F");
        startUrls.add("http://www.nfqbyp.com/news.html?newsTypeID=16804&newsType=%E4%BA%A4%E6%B5%81%E5%A4%A9%E5%9C%B0");
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
            page.putField("advisoryNews", new AdvisoryNews(title, content, band, type, timeDate));
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
