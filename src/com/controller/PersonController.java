package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.utils.MD5;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-12-01
 * @Time:17:16
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/person")
public class PersonController {
    @Autowired
    UserDao userDao;

    @GetMapping("/info")
    public Map<String, Object> info(@RequestParam("username") String username) {
        return R.r(userDao.getByUsername(username), "", 200);
    }

    @PostMapping("/updateInfo")
    public Map<String, Object> updateInfo(@RequestBody User user) {
        User byUsername = userDao.getByUsername(user.getUsername());
        List<User> userKey = userDao.getUserKey(user);
        for (User u : userKey) {
            if (u != null) {
                if (!byUsername.getPhone().equals(user.getPhone())) {
                    if (u.getPhone().equals(user.getPhone())) {
                        return R.r(null, "手机号已存在", 400);
                    }
                }
            }
        }
        //手机号正则
        String phone_regex = "1[35789][0-9]{9}";
        if (!user.getPhone().matches(phone_regex)) {
            return R.r(null, "手机号不合法", 400);
        }
        //身份证正则表达式
        String id_regex = "[1-9]\\d{5}(19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]";
        if (!user.getIdentity().matches(id_regex)) {
            return R.r(null, "身份证号不合法", 400);
        }
        userDao.updatePersonInfo(user);
        return R.r(null,"修改成功",200);
    }

    @PostMapping("/updatePwd")
    public Map<String, Object> updatePassword(@RequestBody Map<String, String> map) {
        User user = userDao.getByUsername(map.get("username"));
        if (user.getPassword().equals(MD5.encoding(map.get("origin")))) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", map.get("username"));
            hashMap.put("new", MD5.encoding(map.get("new")));
            userDao.updatePersonPwd(hashMap);
            return R.r(null, "密码修改成功", 200);
        }
        return R.r(null, "原密码不正确", 400);
    }

}
