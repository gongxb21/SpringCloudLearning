package com.forezp;

import com.forezp.redis.RedisController;
import com.sun.media.jfxmedia.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceHiApplicationTests {

    @Autowired
    RedisController controller;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSave() {
        controller.save();
    }

    @Test
    public void testSet() {
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        Set<String> set = ops.members("set");
        set.forEach(System.out::println);
        ops.add("set","aa","bbb");

        System.out.println("=================");
        Set<String> set2=ops.difference("set","set2");
        set2.forEach(System.out::println);

        System.out.println("=================");
        System.out.println(ops.differenceAndStore("set","set2","sets"));

        System.out.println("=============");
        Set<String> set3= ops.intersect("set","set2");
        set3.forEach(System.out::println);

        System.out.println("======================");
        System.out.println(ops.intersectAndStore("set","set2","setss"));
        System.out.println("======================");
        System.out.println(ops.isMember("set","gongxb"));
        System.out.println("======================");
        System.out.println(ops.randomMember("set"));
        System.out.println("======================");
        System.out.println(ops.pop("set"));
        System.out.println("======================");
        System.out.println(ops.move("set","aa","set2"));
        System.out.println("======================");
        Set<String> set5=ops.union("set","set2");
        set5.forEach(System.out::println);
        System.out.println("=========");
        System.out.println(ops.unionAndStore("set","set2","setSSS"));
    }

    @Test
    public void testHash(){
        HashOperations<String, String, String> ops=stringRedisTemplate.opsForHash();
        System.out.println(ops.get("hash","a"));
        System.out.println("==========");

        Map<String,String> map =ops.entries("hash");
        System.out.println(map);
        System.out.println("=========");

        System.out.println(ops.hasKey("hash","a"));
        System.out.println(ops.hasKey("hash","31.3"));

        System.out.println("=========");
        ops.put("hash","aaaa","4");
        System.out.println(ops.get("hash","aaaa"));
        System.out.println("===========");

        ops.delete("hash","aaaa");
        System.out.println(ops.get("hash","aaaa"));
        System.out.println("===========");

        List<String> list =new ArrayList<>(10);
        list.add("a");
        list.add("aa");
        list.add("aaa");
        System.out.println( ops.multiGet("hash",list));

        Map<String,String> map2=new HashMap(10);
        map2.put("b","1");
        map2.put("bb","11");
        map2.put("bbbb","111");
        ops.putAll("hash",map);

        System.out.println("==============");

        System.out.println(ops.size("hash"));

        System.out.println("==================");

        System.out.println(ops.increment("hash","b",9));

        System.out.println("================");

        System.out.println(ops.get("hash","b"));

        System.out.println("=================");
        System.out.println( ops.values("hash"));
        System.out.println("======================");

        System.out.println(ops.putIfAbsent("hash","b","b"));
        System.out.println(ops.putIfAbsent("hash","bbbb","1111"));
    }

    @Test
    public void testList(){
        ListOperations<String, String> ops=stringRedisTemplate.opsForList();
//        ops.leftPush("java","string");
//        ops.leftPush("java","int");
//        ops.rightPush("java","double");
//        ops.set("java",1,"hello");
//        System.out.println(ops.rightPop("java"));
//
//        ops.rightPush("java","1","2");

//        ops.remove("java",2,"int");
        System.out.println(ops.size("java"));

        ops.trim("java",0,0);


    }


    @Test
    public void testZset(){
        ZSetOperations<String, String> ops= stringRedisTemplate.opsForZSet();
        ops.add("zset","1",0.1);
        ops.add("zset","2",0.2);
        System.out.println(ops.count("zset",0,10));
        System.out.println("=========================");

        ops.incrementScore("zset","1",10);
        System.out.println(ops.count("zset",0,10));
        System.out.println("=========================");
        System.out.println(ops.score("zset","1"));
        System.out.println("========================");
        Set<String>  stringZset= ops.range("zset",0,-1);
        System.out.println(stringZset);

        System.out.println("===============");
        System.out.println(ops.rank("zset","1"));
        System.out.println(ops.rank("zset","gongxb"));

        System.out.println("==================");
        System.out.println(ops.zCard("zset"));
        System.out.println("==================");
        System.out.println(ops.size("zset"));
        System.out.println("==================");
        System.out.println(ops.rangeByScore("zset",0.1 ,100));
        System.out.println("===================");
        Set<ZSetOperations.TypedTuple<String>> sets=ops.rangeByScoreWithScores("zset",0.1,100);
        sets.forEach(s->{
            System.out.println(s.getScore()+":"+s.getValue());
        });
        System.out.println("===================");
        ops.add("zset","1",11);
        //当按照分数从高到低排序时，这个元素的位置
        System.out.println(ops.reverseRank("zset","2"));
        System.out.println("=======================");
        Cursor<ZSetOperations.TypedTuple<String>> cursor=ops.scan("zset",ScanOptions.NONE);
        while(cursor.hasNext()){
            ZSetOperations.TypedTuple<String> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }

    }



}
