package com.raozk.shanghai;

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
public class SHTZGGPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(SHTZGGPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.shscce.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "08";
    //private static String type = "3";


    static {
        startUrls.add("http://www.shscce.com/html/shscce/zxgg/index_1.shtml");
    }

    public void process(Page page) {
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("div[@class='pages']").links().all());
        List<Selectable> links = page.getHtml().xpath("div[@class='bd']/ul/li").nodes();
        LinkedList<String> temp = new LinkedList<String>();
        for(Selectable selectable : links) {
            String title = selectable.xpath("a/text()").get();
            if(StringUtils.hasText(title)){
                String link = selectable.links().get();
                if (!crawed(band, getTypeFromTitle(title), link)) {
                    temp.addFirst(link);
                }
            }
        }
        page.addTargetRequests(temp);
        List<Selectable> nodes = page.getHtml().xpath("div[@class='detail']/div[@class='bd']/").nodes();
        if(nodes!=null && nodes.size()==3){
            String title = nodes.get(0).xpath("div/text()").get();
            String content = nodes.get(2).get();
            String time = nodes.get(1).xpath("div/text()").get();
            if(StringUtils.hasText(time)){
                time = time.split("：")[1];
            }
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
                page.putField("announcement", new Announcement(title, content, band, getTypeFromTitle(title), timeDate));
                logger.info("crawed:"+title);
            }
        }
    }

    private static String getTypeFromTitle(String title){
        if(title==null){
            return null;
        }else if(title.indexOf("托管入库的公告")>=0){
            return "1";
        }else if(title.indexOf("【申购】")>=0){
            return "2";
        }else{
            return "3";
        }
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
