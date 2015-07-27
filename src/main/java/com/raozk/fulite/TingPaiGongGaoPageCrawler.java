package com.raozk.fulite;

import com.raozk.crawler.AbstractBaseCrawler;
import com.raozk.modole.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class TingPaiGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(TingPaiGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://dzp.wjybk.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "07";
    private static String type = "停牌公告";


    static {
        startUrls.add("http://dzp.wjybk.com/?action-category-catid-172");
    }

    private static Map<String, String> dateMap = new HashMap<String, String>();

    public void process(Page page) {
        //page.addTargetRequests(page.getHtml().xpath("div[@class='pages']").links().all());
        LinkedList<String> temp = new LinkedList<String>();
        List<Selectable> links = page.getHtml().xpath("ul[@class='global_tx_list4']/li").nodes();
        for(Selectable link : links){
            String linkUrl = link.links().all().get(0);
            if (!crawed(linkUrl)) {
                temp.addFirst(linkUrl);
            }
            String date = link.xpath("span[@class='box_r']/text()").get();
            dateMap.put(linkUrl, date);
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//div[@id='article']/h1/text()").get();
        String content = page.getHtml().xpath("//div[@id='article_body']/html()").get();
        String time = dateMap.get(page.getRequest().getUrl());
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            //2015/7/8 21:55:53
            //page.putField("announcement", new Announcement(title, content, band, type, time));
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            } catch (ParseException e) {
                logger.error("parse time error", e);
                try {
                    timeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
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
