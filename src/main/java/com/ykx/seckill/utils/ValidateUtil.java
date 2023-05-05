package com.ykx.seckill.utils;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Component
public class ValidateUtil {
    public static final Pattern mobile_pattern = Pattern.compile("^1[3,4,5,6,7,8,9][0-9]{9}$");
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
