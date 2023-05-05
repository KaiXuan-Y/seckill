package com.ykx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ykx.seckill.exception.GlobalException;
import com.ykx.seckill.mapper.UserMapper;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.service.IUserService;
import com.ykx.seckill.utils.CookieUtil;
import com.ykx.seckill.utils.MD5Util;
import com.ykx.seckill.utils.UUIDUtil;
import com.ykx.seckill.vo.LoginVo;
import com.ykx.seckill.vo.RespBean;
import com.ykx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-05-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    UserMapper userMapper;
    @Override
    public RespBean login(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response)   {
        User user = userMapper.selectById(loginVo.getMobile());
        if(user == null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDbPass(loginVo.getPassword() , user.getSlat()).equals(user.getPasword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成Cookie
        String ticket = UUIDUtil.uuid();
        //用户信息放在session中
//        request.getSession().setAttribute(ticket , user);
        redisTemplate.opsForValue().set("user:" + ticket , user);
        CookieUtil.setCookie(request , response , "userTicket" , ticket);
        return RespBean.success();
    }

    @Override
    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if(null != user){
            CookieUtil.setCookie(request , response , "userTicket" , ticket);
        }
        return user;
    }


}
