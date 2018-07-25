package com.forezp.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        System.out.println("this is a ribbon aaaa");
        return restTemplate.getForObject("http://SERVICE-HI/hi?name={1}", String.class, name);
    }

    @HystrixCommand(fallbackMethod = "hiError")
    /**
     * 调用这个方法会报错  No instances available for 192.168.1.101
     * 仔细一想也没有什么毛病，如果IP都固定了，那么要负载均衡有什么用，好简单粗暴的道理
     */
    public String hiServiceByLoadBalanceClient(String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("SERVICE-HI");
        String host= serviceInstance.getHost();
        int port=serviceInstance.getPort();
        logger.info("host={}",host);
        logger.info("port={}",port);
        logger.info("url={}","http://"+host+":"+port+"/hi?name="+name);
        return restTemplate.getForObject("http://"+host+":"+port+"/hi?name="+name,String.class);

    }

    /**
     * 注意getForObject 和getForEntity的区别
     *
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService2(String name) {
        Map<String, String> map = new HashMap<>(1);
        map.put("myName", name);
        return restTemplate.getForObject("http://SERVICE-HI/hi?name={myName}", String.class, map);
    }

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService3(String name) {
        ResponseEntity<String> res = restTemplate.getForEntity("http://SERVICE-HI/hi?name={1}", String.class, name);
        return res.getBody();
    }

    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }
}
