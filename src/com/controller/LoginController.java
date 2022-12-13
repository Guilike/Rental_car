package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.utils.MD5;
import com.utils.R;
import com.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-17
 * @Time:16:35
 * @Description:
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserDao userDao;


    @PostMapping("/check")
    public Map<String, Object> loginCheck(@RequestBody User user) {
        user.setPassword(MD5.encoding(user.getPassword()));
        User u = userDao.loginCheck(user);

        if (u == null) {
            return R.r(null, "账号或密码错误！", 400);
        }
        HashMap<String, Object> data = new HashMap<>();
        try {
            HashMap<String, String> payload = new HashMap<>();
            payload.put("username", u.getUsername());
            payload.put("role", String.valueOf(u.getRole()));
            String token = TokenUtils.getToken(payload);
            u.setToken(token);
            userDao.updateToken(u);
            data.put("username", u.getUsername());
            data.put("identity", u.getIdentity());
            data.put("role", u.getRole());
            data.put("token", token);
            return R.r(data, "登录成功", 200);
        } catch (Exception e) {
            return R.r(null, "登录失败", 401);
        }
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        List<User> userKey = userDao.getUserKey(user);
        for (User u : userKey) {
            //判断用户名和手机号是否已存在
            if (u != null) {
                if (u.getUsername().equals(user.getUsername())) {
                    return R.r(null, "用户名已存在", 400);
                } else if (u.getPhone().equals(user.getPhone())) {
                    return R.r(null, "手机号已存在", 400);
                }
            }
        }


        if ("".equals(user.getUsername()) || "".equals(user.getPhone()) || "".equals(user.getPassword())) {
//            System.out.println("请输入用户名");
            return R.r(null, "注册失败", 400);
        }
        //手机号匹配
        String regex = "1[35789][0-9]{9}";
        if (!user.getPhone().matches(regex)) {
            return R.r(null, "手机号不合法", 400);
        }
        user.setRole(2);
        user.setPassword(MD5.encoding(user.getPassword()));
        userDao.addUser(user);
        return R.r(null, "注册成功", 200);
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestBody User user) {
        User u = userDao.getByUsername(user.getUsername());
        u.setToken(null);
        if (userDao.updateToken(u) == 1) {
            return R.r(null, "已退出", 200);
        }
        return R.r(null, "错误", 400);
    }

}
