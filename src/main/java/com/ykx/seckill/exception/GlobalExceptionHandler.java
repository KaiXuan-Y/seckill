package com.ykx.seckill.exception;

import com.ykx.seckill.vo.RespBean;

import com.ykx.seckill.vo.RespBeanEnum;
import org.apache.ibatis.binding.BindingException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBean());

        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;

        }
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
