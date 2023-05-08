package com.ykx.seckill.controller;

import com.ykx.seckill.pojo.Order;
import com.ykx.seckill.pojo.SeckillOrder;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.service.IGoodsService;
import com.ykx.seckill.service.IOrderService;
import com.ykx.seckill.service.ISeckillGoodsService;
import com.ykx.seckill.service.ISeckillOrderService;
import com.ykx.seckill.vo.GoodsVo;
import com.ykx.seckill.vo.RespBean;
import com.ykx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2023/5/5.
 *
 * @author KaiXuan Yang
 */
@Controller
@RequestMapping("/seckills")
public class SecKillController {

    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IOrderService orderService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 优化前QPS：155
     *
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    @Transactional
    @ResponseBody
    public RespBean doSecKill(Model model, User user, Long goodsId) {
        if (null == user) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        model.addAttribute("user", user);
        if (goodsService.checkShockEnough(goodsId)) {
            model.addAttribute("errmsg", RespBeanEnum.STOCK_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.STOCK_ERROR);
        }

//        SeckillOrder seckillOrder = seckillOrderService.checkDoubleOrder(user.getId() , goodsId);
//        if(null != seckillOrder){
//            model.addAttribute("errmsg" , RespBeanEnum.REPEAT_ERROR.getMessage());
//            r
//        }
        GoodsVo goods = goodsService.getGoodsDetailById(goodsId);
        SeckillOrder seckillOrder1 = (SeckillOrder) redisTemplate.opsForValue().get("userId:" + user.getId() + "goodsId:" + goodsId);
        if (null != seckillOrder1) {
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR.getMessage());
        }
        Order order = orderService.secKill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return RespBean.success(order);


    }
    @RequestMapping("/hello")
    public String tests(User user){
        return "login";
    }
}
