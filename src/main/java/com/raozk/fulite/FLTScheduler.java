package com.raozk.fulite;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.AdvisoryNewsPipeline;
import com.raozk.piprline.AnnouncementPipeline;
import com.raozk.scheduler.BaseSchduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class FLTScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(FLTScheduler.class);

    @Resource(type = FLTSGGGPageCrawler.class)
    BaseCrawler fLTSGGGPageCrawler;

    @Resource(type = FLTTGGGPageCrawler.class)
    BaseCrawler fLTTGGGPageCrawler;

    @Resource(type = FLTZXPageCrawler.class)
    BaseCrawler fLTZXPageCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    @Resource(type = AdvisoryNewsPipeline.class)
    Pipeline advisoryNewsPipeline;

    public void run() {
    }

    @Scheduled(cron = "0 40 22 * * ?")
    public void fLTSGGGPage() {
        Spider spider = Spider.create(fLTSGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : fLTSGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 40 22 * * ?")
    public void fLTTGGGPage() {
        Spider spider = Spider.create(fLTTGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : fLTTGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 40 22 * * ?")
    public void zxPage() {
        Spider spider = Spider.create(fLTZXPageCrawler).addPipeline(advisoryNewsPipeline);
        for(String startUrl : fLTZXPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ShangShiGongGaoPageCrawler.class)
    BaseCrawler ShangShiGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void ShangShiGongGaoPageCrawler() {
        Spider spider = Spider.create(ShangShiGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : ShangShiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JiaoYiGongGaoPageCrawler.class)
    BaseCrawler JiaoYiGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void JiaoYiGongGaoPageCrawler() {
        Spider spider = Spider.create(JiaoYiGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : JiaoYiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(type = ZhongQianGongGaoPageCrawler.class)
    BaseCrawler ZhongQianGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void ZhongQianGongGaoPageCrawler() {
        Spider spider = Spider.create(ZhongQianGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : ZhongQianGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = TingPaiGongGaoPageCrawler.class)
    BaseCrawler TingPaiGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void TingPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(TingPaiGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : TingPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(name="fltJiaoYiShuJuPageCrawler")
    JiaoYiShuJuPageCrawler fltJiaoYiShuJuPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void JiaoYiShuJuPageCrawler() {
        Spider spider = Spider.create(fltJiaoYiShuJuPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : fltJiaoYiShuJuPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = XianHuoCanKaoJiaPageCrawler.class)
    BaseCrawler XianHuoCanKaoJiaPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void XianHuoCanKaoJiaPageCrawler() {
        Spider spider = Spider.create(XianHuoCanKaoJiaPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : XianHuoCanKaoJiaPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }




}
