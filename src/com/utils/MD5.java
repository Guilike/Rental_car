package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @User:桂炯
 * @Date:2022-12-03
 * @Time:10:34
 * @Description:
 */
public class MD5 {
    public static String encoding(String password){
        try {
            //获取信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] res = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            /**
             * 把每一个byte和0xff做与运算
             * 任意数与0xff进行运算都是取16进制的后两位
             * 自定义for循环
             */
            for (byte b : res) {
                int number = b & 0xff;  //加盐
                String s = Integer.toHexString(number); //转化为16进制
                if (s.length() == 1){
                    buffer.append("0");
                }
                buffer.append(s);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
