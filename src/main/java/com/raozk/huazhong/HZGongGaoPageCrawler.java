package com.raozk.huazhong;

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
public class HZGongGaoPageCrawler extends AbstractBaseCrawler {

    private static Logger logger = LoggerFactory.getLogger(HZGongGaoPageCrawler.class);

    private Site site = Site.me().setDomain("http://www.hbcpre.com/");

    private static List<String> startUrls = new LinkedList<String>();

    private static String band = "09";
    //private static String type = "3";


    static {
        startUrls.add("http://www.hbcpre.com/index.php/index-show-tid-26.html");
    }

    public void process(Page page) {
        if("1".equals(appconfig.get("crawAll"))) page.addTargetRequests(page.getHtml().xpath("div[@class='page']").links().all());
        List<Selectable> links = page.getHtml().xpath("div[@class='news']/ul/li").nodes();
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
        List<Selectable> nodes = page.getHtml().xpath("div[@class='newsfi']/").nodes();
        if(nodes!=null && nodes.size()>=3){
            String title = nodes.get(0).xpath("div/text()").get();
            String time = nodes.get(1).xpath("div/text()").get();
            if(StringUtils.hasText(time)){
                time = time.split("：")[1].split("信息来源")[0];
            }
            StringBuffer sb = new StringBuffer();
            for(int i=2; i<nodes.size(); i++){
                sb.append(nodes.get(i).get());
            }
            String content = sb.toString();
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
        }else if(title.indexOf("托管结果公告")>=0 || title.indexOf("托管产品公告")>=0){
            return "1";
        }else if(title.indexOf("申购公告")>=0){
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
