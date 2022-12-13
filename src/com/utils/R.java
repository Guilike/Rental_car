package com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-19
 * @Time:10:10
 * @Description:
 */
public class R {
    public static Map<String, Object> r(Object o, String msg, int code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", o);
        map.put("msg", msg);
        map.put("code", code);
        return map;
    }
}
