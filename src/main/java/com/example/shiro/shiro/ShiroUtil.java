package com.example.shiro.shiro;

import com.alibaba.fastjson.JSON;
import com.example.shiro.vo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


public class ShiroUtil {

    public static final String USER_INFO = "userInfo";

    public static final String TOKEN_ID = "tokenId";

    public static UserInfo getUserInfo(){
        Session session = getSession();
        Object userInfo = session.getAttribute(USER_INFO);
        if(userInfo == null){
            return null;
        }
        return JSON.parseObject(userInfo.toString(),UserInfo.class);
    }

    public static Session getSession(){
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    public static boolean hasUserRole(String role){
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role);
    }

    public static void setAttribute(Object key, Object value) {
        Session session = getSession();
        session.setAttribute(key, value);
    }

    public static Object getAttribute(Object key) {
        Session session = getSession();
        return session.getAttribute(key);
    }
}
