package com.forezp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author gongxb
 * @date 2018/9/13
 * @desc
 * @return
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public void save() {
        logger.info("invoke save method");
        stringRedisTemplate.opsForValue().getAndSet("zzp", "aaaa");
        stringRedisTemplate.opsForValue().set("redis", "time", 10, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set("ran", "time", 1);
        String ran = stringRedisTemplate.opsForValue().get("ran");
        logger.info("ran={}", ran);
        String redis = stringRedisTemplate.opsForValue().get("redis");
        logger.info("redis={}", redis);

        User user = new User("a", "b");
        stringRedisTemplate.opsForValue().set("user", user.toString());
        stringRedisTemplate.opsForValue().get("user");
        logger.info("User={}", user);

        logger.info("hasKey user={}", stringRedisTemplate.hasKey("user"));
        logger.info("hasKey user={}", stringRedisTemplate.hasKey("users"));

        stringRedisTemplate.boundValueOps("num").increment(1);
        logger.info("num={}",stringRedisTemplate.opsForValue().get("num"));
        stringRedisTemplate.boundValueOps("num").increment(-1);
        logger.info("num2={}",stringRedisTemplate.opsForValue().get("num"));

        logger.info("redis={}",stringRedisTemplate.getExpire("redis"));
        logger.info("redis2={}",stringRedisTemplate.getExpire("redis",TimeUnit.MICROSECONDS));

        logger.info("ismember={}",stringRedisTemplate.opsForSet().isMember("set","3"));

       Set<String> set= stringRedisTemplate.opsForSet().members("set");
       set.forEach(System.out::println);


    }

    @RequestMapping(value = "/set/redis" ,method = RequestMethod.GET)
    public void setRedis(){
        User user=new User("1","2");
        redisTemplate.opsForValue().set("redisUser",user);

        User user2 =(User) redisTemplate.opsForValue().get("redisUser");
        logger.info("user2 ={}",user2);

    }
}
