package com.ykx.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Component
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);

    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0) + salt.charAt(1) + salt.charAt(3) + inputPass + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt) {
        String str = ""+salt.charAt(0) + salt.charAt(1) + salt.charAt(3) + formPass + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDbPass(formPass, salt);
        return dbPass;
    }

//    public static void main(String[] args) {
//        System.out.println(inputPassToFormPass("123456"));
//        System.out.println(formPassToDbPass("123456","1a2b3c4d"));
//        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
//    }


}
