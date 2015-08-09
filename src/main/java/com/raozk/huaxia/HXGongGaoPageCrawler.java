package com.raozk.huaxia;

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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class HXGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(HXGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.huaxiacae.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "11";
    private static String type = "3";


    static {
        startUrls.add("http://www.huaxiacae.com/notice/Latest/");
        startUrls.add("http://www.huaxiacae.com/notice/tpgg/");
        startUrls.add("http://www.huaxiacae.com/notice/delivery/");
    }

    public void process(Page page) {//http://www.nfqbyp.com/infomation.html?pageIndex=2&newsTypeID=16793&newsType=%E4%B8%AD%E5%BF%83%E9%80%9A%E5%91%8A
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("table[@class='list_page']").links().all());
        List<String> links = page.getHtml().xpath("table[@class='box']/tbody/tr/td/ul/").links().all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(band, type, link)) {
                temp.addFirst(link);
           }
        }
        page.addTargetRequests(temp);
        List<Selectable> nodes = page.getHtml().xpath("//td[@class='main']/table[@class='box']/tbody/tr/td/").nodes();

        if(nodes.size()==3){
            String title = nodes.get(0).xpath("//table[@class='title_info']/tbody/tr/td/h1/span/text()").get();
            String time = page.getHtml().xpath("//td[@class='info_text']/text()").get();
            String content = nodes.get(1).get();
            if(StringUtils.hasText(time)){
                time = time.split("点击数")[0].split("：")[1].trim();
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
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
