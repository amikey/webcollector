package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import com.raozk.modole.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

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

    boolean addCrawed(String url){
        return redisTemplate.opsForSet().add("001:00",url)==1?true:false;
    }

    public void process(ResultItems resultItems, Task task) {
        Announcement announcement = resultItems.get("announcement");
        if(announcement!=null && addCrawed(resultItems.getRequest().getUrl())){
            //if(announcement!=null){
            saveAnnoucement2Redis(announcement);
            saveAnnoucement2Mysql(announcement);
            pubAnnoucement(announcement);
        }
    }

    private void saveAnnoucement2Redis(Announcement announcement){
        redisTemplate.opsForList().leftPush("001:01", JSON.toJSONString(announcement));
    }

    private void saveAnnoucement2Mysql(Announcement announcement){
        jdbcTemplate.update("insert into t_announcement(title, content, time, exchange, type, st) values(?,?,?,?,?,?)",
                announcement.getTitle(), announcement.getContent(), announcement.getTime(), announcement.getExchange(), announcement.getType(), announcement.getSt());
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
        publisher.send("ac", 0);
        publisher.send(JSON.toJSONString(announcement), 0);
    }

    @PreDestroy
    private void destroy(){
        publisher.send("END", 0);
        publisher.close();
        context.term();
    }
}
