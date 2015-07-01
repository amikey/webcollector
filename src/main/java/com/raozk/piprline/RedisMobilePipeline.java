package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import org.jsoup.helper.StringUtil;
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
public class RedisMobilePipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(RedisMobilePipeline.class);

    @Resource(name="mobileRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    public void process(ResultItems resultItems, Task task) {
        Map<String, String> map = new HashMap<String, String>();
        String text = resultItems.get("content");
        if(StringUtils.isEmpty(text)){
            return;
        }

        List<String> mobiles = getMobileNo(text);

        if(mobiles.size()==0){
            return;
        }

        String truename = text.substring(text.indexOf("姓名")+3).trim();
        truename = truename.substring(0, truename.indexOf("（"));

        map.put("truename", truename);
        map.put("content", text);

        for(String mobile : mobiles){
            redisTemplate.opsForValue().set("013:"+mobile, JSON.toJSONString(map));
        }
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

}
