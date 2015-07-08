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

import javax.annotation.Resource;

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
            saveAnnoucement2Redis(announcement);
            saveAnnoucement2Mysql(announcement);
        }
    }

    private void saveAnnoucement2Redis(Announcement announcement){
        redisTemplate.opsForList().leftPush("001:01", JSON.toJSONString(announcement));
    }

    private void saveAnnoucement2Mysql(Announcement announcement){
        jdbcTemplate.update("insert into t_announcement(title, content, time, band, type, st) values(?,?,?,?,?,?)",
                announcement.getTitle(), announcement.getContent(), announcement.getTime(), announcement.getBand(), announcement.getType(), announcement.getSt());
    }
}
