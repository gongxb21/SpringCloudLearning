package com.forezp.web;

import com.forezp.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@RestController
public class HelloControler {
    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @RequestMapping(value = "/hi2")
    public String hi2(@RequestParam String name) {
        return helloService.hiService2(name);
    }

    @RequestMapping(value = "/hi3")
    public String hi3(@RequestParam String name) {
        return helloService.hiService3(name);
    }

    @RequestMapping(value = "/hi4", method = RequestMethod.POST)
    public String hi4(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @RequestMapping(value = "/hi5", method = RequestMethod.POST)
    public String hi5(@RequestParam String name) {
        return helloService.hiService2(name);
    }

    @RequestMapping(value = "/hi6", method = RequestMethod.POST)
    public String hi6(@RequestParam String name) {
        return helloService.hiService3(name);
    }

    @RequestMapping(value = "/hi7")
    public String hiByLoadBalanceClient(@RequestParam String name) {
        return helloService.hiServiceByLoadBalanceClient(name);
    }
}
