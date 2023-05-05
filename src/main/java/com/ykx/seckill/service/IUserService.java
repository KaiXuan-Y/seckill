package com.ykx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ykx.seckill.exception.GlobalException;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.vo.LoginVo;
import com.ykx.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-05-04
 */
public interface IUserService extends IService<User> {

    RespBean login(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) throws GlobalException;

    User getUserByCookie(String ticket , HttpServletRequest request , HttpServletResponse response);
}
