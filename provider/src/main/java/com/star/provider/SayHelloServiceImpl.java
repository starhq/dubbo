package com.star.provider;

import com.star.SayHelloService;
import org.springframework.stereotype.Service;

/**
 * Created by starhq on 2016/11/18.
 */
@Service("sayHelloService")
public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public void sayHelloWorld(String name) {
        System.out.println("Hello: " + name);
    }
}
