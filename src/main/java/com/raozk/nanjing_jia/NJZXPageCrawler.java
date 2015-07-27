package com.raozk.nanjing_jia;

import com.raozk.crawler.AbstractBaseCrawler;
import com.raozk.modole.AdvisoryNews;
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
public class NJZXPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(NJZXPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.njwjsqbyp.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "01";
    private static String type = "咨询";


    static {
        startUrls.add("http://www.njwjsqbyp.com/list.asp?id=4");
    }

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://www\\.njwjsqbyp\\.com/list\\.asp\\?id=4&Page=\\d+").all());
        List<String> links = page.getHtml().links().regex("http://www\\.njwjsqbyp\\.com/show\\.asp\\?id=\\d+").all();
        LinkedList<String> temp = new LinkedList<String>();
        for(String link : links) {
            if (!crawed(link)) {
                temp.addFirst(link);
            }
        }
        page.addTargetRequests(temp);
        String title = page.getHtml().xpath("//div[@class='neirong_title']/text()").get();
        String content = page.getHtml().xpath("//div[@class='neirong_text']/html()").get();
        String time = page.getHtml().xpath("//div[@class='neirong_note']/text()").get();
        if(StringUtils.hasText(time)){
            time = time.substring(0,17);
        }
        if(StringUtils.hasText(title)&&StringUtils.hasText(content)) {
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                logger.error("parse time error", e);
                try {
                    timeDate = new SimpleDateFormat("yyyy/MM/dd").parse(time);
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
