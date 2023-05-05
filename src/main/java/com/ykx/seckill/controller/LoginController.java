package com.ykx.seckill.controller;

import com.ykx.seckill.exception.GlobalException;
import com.ykx.seckill.service.IUserService;
import com.ykx.seckill.vo.LoginVo;
import com.ykx.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    IUserService userService;

    /**
     * f
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public String login() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Validated LoginVo loginVo , HttpServletRequest request , HttpServletResponse response)  {
        log.info("{}", loginVo);
        return userService.login(loginVo , request , response);
    }
}
