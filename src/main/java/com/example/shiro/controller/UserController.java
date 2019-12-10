package com.example.shiro.controller;

import com.example.shiro.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getUser")
    public Object getUser(){
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo", ShiroUtil.getUserInfo());
        map.put("port",port);
        return map;
    }
}
