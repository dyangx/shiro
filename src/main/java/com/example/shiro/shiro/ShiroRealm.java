package com.example.shiro.shiro;

import com.alibaba.fastjson.JSONObject;
import com.example.shiro.service.UserService;
import com.example.shiro.vo.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权认证
     * @param
     * @return 
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 登陆认证
     * @param
     * @return 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        Session session = ShiroUtil.getSession();
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String name = token.getUsername();
        String pwd = new String(token.getPassword());
        UserInfo userInfo = userService.findUserByName(name);
        if(userInfo == null){
            throw new UnknownAccountException("账户不存在！");
        }else if(userInfo.getPwd().equals(pwd)){
            session.setAttribute(ShiroUtil.USER_INFO, JSONObject.toJSONString(userInfo));
            return new SimpleAuthenticationInfo(token.getUsername(),token.getPassword(),this.getName());
        }else{
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
    }
}
