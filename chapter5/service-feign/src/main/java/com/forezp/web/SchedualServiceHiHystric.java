package com.forezp.web;

import com.forezp.service.SchedualServiceHi;
import org.springframework.stereotype.Component;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
