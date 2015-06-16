package com.raozk.piprline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class RedisGGPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(RedisGGPipeline.class);

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public void process(ResultItems resultItems, Task task) {
        /*String url = resultItems.getRequest().getUrl();
        String title = resultItems.get("title");
        String content = resultItems.get("content");*/
        redisTemplate.opsForHash().putAll(resultItems.getRequest().getUrl(), resultItems.getAll());
    }
}
