package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class RedisXianjiaPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(RedisXianjiaPipeline.class);

    public void process(ResultItems resultItems, Task task) {


    }



}
