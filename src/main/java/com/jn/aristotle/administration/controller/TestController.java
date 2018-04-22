package com.jn.aristotle.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/sind")
@RestController
public class TestController{

    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("/cs")
    public String cs(){
        redisTemplate.opsForValue().set("BC:20180403:XWC","549595");
        return "启动成功";
    }
}
