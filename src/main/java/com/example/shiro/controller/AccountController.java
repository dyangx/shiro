package com.example.shiro.controller;

import com.example.shiro.service.UserService;
import com.example.shiro.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired UserService userService;

    @RequestMapping("/login")
    public Object login(String name,String pwd){
        try {
            userService.login(name,pwd);
            return ShiroUtil.getUserInfo();
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/loginOut")
    public Object loginOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "登出成功！";

    }
}
