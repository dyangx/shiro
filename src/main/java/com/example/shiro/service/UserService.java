package com.example.shiro.service;

import com.example.shiro.vo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public void login(String name,String pwd){
        UsernamePasswordToken token = new UsernamePasswordToken(name,pwd,false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            subject.logout();
            throw new RuntimeException("登录失败！");
        }
    }

    public UserInfo findUserByName(String name){
        if("yy".equals(name)){
            return new UserInfo("No.1","yy","123");
        }
        return null;
    }

}
