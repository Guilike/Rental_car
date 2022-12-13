package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.utils.MD5;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-12-01
 * @Time:14:43
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    UserDao userDao;

    @GetMapping("/list")
    public Map<String, Object> getAllList() {
        return R.r(userDao.getAllList(), "获取数据", 200);
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody User user) {
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

        if (user.getUsername() == null || user.getPhone() == null || user.getIdentity() == null) {
            return R.r(null, "新增失败", 400);
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

        user.setPassword(MD5.encoding(user.getIdentity().substring(12)));
        userDao.add(user);
        return R.r(null, "新增成功", 200);
    }

    @PostMapping("/delete")
    public Map<String, Object> del(@RequestBody User user) {
        if (user.getUsername().equals("admin")) {
            return R.r(null, "删除失败", 400);
        }
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/deleteIds")
    public Map<String, Object> delIds(@RequestBody Map<String, String[]> ids) {
        List<String> o = Arrays.asList(ids.get("ids"));
        for (String s : o) {
            if ("1".equals(s)) {
                return R.r(null, "删除失败", 400);
            }
        }
        if (userDao.delIds(o) == 0) {
            return R.r(null, "删除失败", 400);
        }
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/update")
    public Map<String, Object> updateCustomer(@RequestBody User user) {
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
        userDao.update(user);
        return R.r(null,"修改成功",200);
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam("type") String type, @RequestParam("searchText") String searchText) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("searchText", searchText);
        hashMap.put("type", type);

        List<User> list = userDao.search(hashMap);
        if (list.isEmpty() || searchText.isEmpty()) {
            return R.r(null, "未查询到数据", 400);
        }
        return R.r(list, "查找成功", 200);
    }

    @GetMapping("/resetpwd")
    public Map<String, Object> resetPassword(@RequestParam("id") int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("password", MD5.encoding("000000"));
        return R.r(userDao.resetPwd(map), "密码已重置", 200);
    }
}
