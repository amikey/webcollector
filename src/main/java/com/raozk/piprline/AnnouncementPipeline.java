package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import com.raozk.modole.Announcement;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class AnnouncementPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(AnnouncementPipeline.class);

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    JdbcTemplate jdbcTemplate;

    boolean addCrawed(String exchange, String type, String url){
        return redisTemplate.opsForSet().add("001:00:"+exchange+":"+type,url)==1?true:false;
    }

    @Transactional
    public void process(ResultItems resultItems, Task task) {
        Announcement announcement = resultItems.get("announcement");
        if(announcement!=null && addCrawed(announcement.getExchange(), announcement.getType(), resultItems.getRequest().getUrl())){
            announcement.setDomain(task.getSite().getDomain());
            announcement.setUrl(resultItems.getRequest().getUrl());
            saveAnnoucement2Mysql(announcement);
            saveAnnoucement2Redis(announcement);
            try{
                pubAnnoucement(announcement);
            }catch (Exception e){
                logger.warn("pub error" ,e);
            }

        }
    }

    private void saveAnnoucement2Redis(Announcement announcement){
        Map<String, Object> redisAnnoucement = new LinkedHashMap<String, Object>();
        redisAnnoucement.put("title", announcement.getTitle());
        redisAnnoucement.put("time", announcement.getTime());
        redisAnnoucement.put("exchange", announcement.getExchange());
        redisAnnoucement.put("type", announcement.getType());
        redisAnnoucement.put("domain", announcement.getDomain());
        redisAnnoucement.put("url", announcement.getUrl());
        redisAnnoucement.put("content", announcement.getContent());
        //redisTemplate.opsForList().leftPush("001:01", JSON.toJSONString(redisAnnoucement));
        redisTemplate.opsForValue().set(DigestUtils.md5Hex(announcement.getContent()), JSON.toJSONString(redisAnnoucement));
    }

    private void saveAnnoucement2Mysql(Announcement announcement){
        jdbcTemplate.update("replace into t_announcement(title, time, exchange, type, domain, url, contentMD5) values(?,?,?,?,?,?,?)",
                announcement.getTitle(), announcement.getTime(), announcement.getExchange(), announcement.getType(),
                announcement.getDomain(), announcement.getUrl(), DigestUtils.md5Hex(announcement.getContent()));
    }

    private Context context = null;
    private Socket publisher = null;

    @PostConstruct
    private void init(){
        context = ZMQ.context(1);
        publisher = context.socket(ZMQ.PUB);
        publisher.setLinger(5000);
        publisher.setSndHWM(0);
        publisher.bind("tcp://*:8888");
    }

    synchronized private void pubAnnoucement(Announcement announcement){
        Map<String, String> pubAnnoucement = new LinkedHashMap<String, String>(2);
        pubAnnoucement.put("title", announcement.getTitle());
        pubAnnoucement.put("contentMD5", DigestUtils.md5Hex(announcement.getContent()));
        publisher.send("yb.ac", 0);
        publisher.send(JSON.toJSONString(pubAnnoucement), 0);
    }

    @PreDestroy
    private void destroy(){
        publisher.send("END", 0);
        publisher.close();
        context.term();
    }
}
