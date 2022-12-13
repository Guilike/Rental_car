package com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-19
 * @Time:21:06
 * @Description:
 */
public class TokenUtils {
    private static String signature = "myTokenKey";
    private static long time = 1000 * 60 * 60 * 24;

    /**
     * 生成token
     *
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        //设置token过期时间为1天
        builder.withExpiresAt(new Date(System.currentTimeMillis() + time));
        return builder.sign(Algorithm.HMAC256(signature));
    }

    /**
     * 验证token
     *
     * @param token
     */
    public static void check(String token) {
        JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }

    /**
     * 获取token中的payload
     *
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token) {
        return JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }
}
