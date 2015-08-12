package com.raozk.zhongyi;

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
public class ZYTuoGuanGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(ZYTuoGuanGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.cacecybk.com.cn");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "05";
    private static String type = "1";

    static {
        startUrls.add("http://www.cacecybk.com.cn/web/news/news.jsp?parentid=44&classid=55");
    }

    private static Map<String, String> titleMap = new HashMap<String, String>();
    private static Map<String, String> dateMap = new HashMap<String, String>();
    public void process(Page page) {
        List<Selectable> links = page.getHtml().xpath("//div[@class='st_rightbor']/div[@class='list']/table/tbody/").nodes();
        LinkedList<String> temp = new LinkedList<String>();
        for(Selectable selectable : links) {
            List<Selectable> urlLine = selectable.xpath("tr/").nodes();
            if(urlLine.size()!=3){
                continue;
            }
            Selectable urlTd = urlLine.get(1);
            String url = urlTd.links().all().get(0);
            String title = urlTd.xpath("td/a/text()").get();
            String time = urlLine.get(2).xpath("td/text()").get();
            titleMap.put(url, title);
            dateMap.put(url, time);
            if (!crawed(band, type, url)) {
                temp.addFirst(url);
            }
        }
        page.addTargetRequests(temp);

        String content = page.getHtml().xpath("//div[@class='news_w']/html()").get();
        if(StringUtils.hasText(content)) {
            String url = page.getRequest().getUrl();
            String title = titleMap.get(url);
            String time = dateMap.get(url);
            if(StringUtils.isEmpty(title)){
                return;
            }
            time =  Calendar.getInstance().get(Calendar.YEAR) + "-" + time;
            Date timeDate = null;
            try {
                timeDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            } catch (ParseException e) {
                //logger.error("parse time error", e);
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
