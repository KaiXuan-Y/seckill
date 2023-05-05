package com.ykx.seckill.vo;

import lombok.*;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200 , "SUCCESS"),
    ERROR(500 , "服务端异常"),
    //用户
    LOGIN_ERROR(500201 , "用户登录错误"),
    MOBILE_ERROR(500202 , "手机号不正确"),

    BIND_ERROR(500203,"参数校验错误"),

    //秒杀
    STOCK_ERROR(500303,"库存不足"),
    REPEAT_ERROR(500304,"拒绝重复秒杀")
    ;

    private final Integer code;
    private final String message;
}
