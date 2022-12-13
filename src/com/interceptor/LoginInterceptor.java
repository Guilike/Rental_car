package com.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dao.UserDao;
import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @User:桂炯
 * @Date:2022-11-19
 * @Time:23:24
 * @Description:
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS请求类型直接返回不处理
//        if ("OPTIONS".equals(request.getMethod())){
//            return false;
//        }
        //在拦截器中设置允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Max-Age","3600");

        HashMap<String, Object> data = new HashMap<>();
        //获取token
        String token = request.getHeader("token");
//        System.out.println(token);
        //校验token
        try {
            DecodedJWT jwt = TokenUtils.getToken(token);
            String name = String.valueOf(jwt.getClaim("username").asString());
            User uToken = userDao.getByUsername(name);
            if (!token.equals(uToken.getToken())) {
                data.put("msg","已过期，请重新登录");
                data.put("code",401);
                String json = new ObjectMapper().writeValueAsString(data);
                response.getWriter().println(json);
                return false;
            }
            TokenUtils.check(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("无效签名");
            data.put("msg", "无效签名");
            data.put("code", 401);
            String json = new ObjectMapper().writeValueAsString(data);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }
    }
}
