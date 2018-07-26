package com.forezp.web;

import com.forezp.service.SchedualServiceHi;
import org.springframework.stereotype.Component;

/**
 * @author gongxb
 * @date 2018/7/21
 * @desc
 * @return
 */
@Component
public class SchedualServiceHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return " sorry ,there is sth wrong"+name;
    }
}
