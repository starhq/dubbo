package com.star.controller;

import com.star.SayHelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by starhq on 2016/11/18.
 */
@Controller
public class Demo {

    @Resource
    private SayHelloService sayHelloService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public void test() {
        sayHelloService.sayHelloWorld("Jack");
    }
}
