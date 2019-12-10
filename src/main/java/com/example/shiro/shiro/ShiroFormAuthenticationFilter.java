package com.example.shiro.shiro;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            //shiro 403无权限访问默认是重定向，重写此处通过json返回
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
                res.setStatus(HttpStatus.OK.value());
                return true;
            }
            res.setHeader("Access-Control-Allow-Origin",  req.getHeader("Origin"));
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setContentType("application/json; charset=utf-8");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            JSONObject result = new JSONObject();
            result.put("message", "登录失效");
            result.put("code", 403);
            result.put("operateSuccess", false);
            out.println(result);
            out.flush();
//            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
}
