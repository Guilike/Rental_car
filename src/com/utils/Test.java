package com.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dao.CarDao;
import com.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @User:桂炯
 * @Date:2022-11-19
 * @Time:21:39
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
//        HashMap<String, Object> data = new HashMap<>();
//        try {
//            HashMap<String, String> payload = new HashMap<>();
//            payload.put("user","lucy");
//            payload.put("pwd","123456");
//            payload.put("role","root");
//
//            //生成token
//            String token = TokenUtils.getToken(payload);
//            System.out.println(token);
//            R.result(token,"认证成功",200);
//
//
//            //检验token
//            TokenUtils.check("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoicm9vdCIsInB3ZCI6IjEyMzQ1NiIsImV4cCI6MTY2OTEwOTExMywidXNlciI6Imx1Y3kifQ.H5NjEnFcMj_ErDDmHfPDoa1Mj0MGPgqSvoTM94FOsQw");
//            DecodedJWT jwt = TokenUtils.getToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMCIsImV4cCI6MTY2OTA5NTExMCwidXNlcm5hbWUiOiJhZG1pbiJ9.EgrKuklnND80hOlAcUxif7xXrUwLs0-H2PayaTp_mHs");
//            System.out.println(jwt.getClaim("username").asString());
//            System.out.println(jwt.getClaim("pwd").asString());
//
//            System.out.println(jwt.getClaim("role").asString());
//            System.out.println(jwt.getExpiresAt());
//        } catch (Exception e) {
//            R.result(null,"认证失败",400);
//        }
//        String str = "450881200009204176";
//        System.out.println(str.substring(12) );

//        LocalDateTime time = LocalDateTime.now();
//        System.out.println(time);
//        System.out.println(LocalDate.now());
//        System.out.println(LocalTime.now());

//        String t = "I";
//        t += LocalDateTime.now().toString();
//        String s = t.replace("-", "").replace(":","").replace(".","");
//        System.out.println(s);

        LocalDateTime end = LocalDateTime.of(2022, 11, 28, 19, 11, 0);
        LocalDateTime start = LocalDateTime.of(2022, 11, 26, 17, 11, 0);
        long l = start.until(end, ChronoUnit.MINUTES);
        System.out.println(start);
        System.out.println(end);
//        System.out.println(l);
        int day = 0;
        if (l / 1440 == 0) {
            day += 1;
        }else{
            if (l % 1440 == 0) {
                day += l / 1440;
            } else {
                day += l / 1440 + 1;
            }
        }

        double d = 20.00;


        System.out.println(day * d);
    }
}
