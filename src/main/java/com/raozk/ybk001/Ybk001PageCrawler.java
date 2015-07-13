package com.raozk.ybk001;

import com.raozk.crawler.AbstractBaseCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class Ybk001PageCrawler extends AbstractBaseCrawler{

    private static Logger logger = LoggerFactory.getLogger(Ybk001PageCrawler.class);

    private Site site = Site.me().setDomain("http://www.ybk001.com/");

    private static List<String> startUrls = new LinkedList<String>();

    static {
        startUrls.add("http://shop.ybk001.com/trade_list.asp?mmp=all&isindex=1&type=new");
        Calendar cal = Calendar.getInstance();
        String date_s = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
        for(int i=1; i < 71; i++){
            startUrls.add("http://shop.ybk001.com/trade_list.asp?type=new&pubman=&mmp=&leibie=&field=name&keyword=&sdate="+date_s+"&edate="+date_s+"&isindex=1&mmm="+i);
        }
    }

    public void process(Page page) {//http://shop.ybk001.com/trade_show.asp?id=5534883
        List<String> links = page.getHtml().xpath("//table[@class='treadlistbox']/").links().regex("http://shop\\.ybk001\\.com/trade_show\\.asp\\?id=\\d+").all();
        page.addTargetRequests(links);
        List<Selectable> details = page.getHtml().xpath("table[@class='detail']").nodes();
        Map<String, Map<String, String>> mobileMap = new HashMap<String, Map<String, String>>();
        for(Selectable detail : details){
            //System.out.println(detail.get());
            String det = detail.get();
            if(StringUtils.hasText(det)){
                List<String> mobiles = getMobileNo(det);
                if(mobiles!=null && mobiles.size()>0){
                    for(String mobile : mobiles){
                        mobileMap.put(mobile, new HashMap<String, String>());
                    }
                }
            }
        }
        page.putField("mobile", mobileMap);
    }

    public static boolean isMobileNO(String text){
        Pattern p = Pattern.compile("((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    public static List<String> getMobileNo(String text){
        Pattern p = Pattern.compile("((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}");
        Matcher m = p.matcher(text);
        List<String> mobiles = new LinkedList<String>();
        while(m.find()){
            mobiles.add(m.group());
        }
        return mobiles;
    }

    public Site getSite() {
        return site;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }
}
