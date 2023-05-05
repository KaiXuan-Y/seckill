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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2023/5/5.
 *
 * @author KaiXuan Yang
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IOrderService orderService;

    @RequestMapping("/doSeckill")
    @Transactional
    public String doSecKill(Model model, User user, Long goodsId) {
        if (null == user) {
            return "login";
        }
        model.addAttribute("user" , user);
        if(goodsService.checkShockEnough(goodsId)){
            model.addAttribute("errmsg" ,RespBeanEnum.STOCK_ERROR.getMessage() );
            return "login";
        }

        SeckillOrder seckillOrder = seckillOrderService.checkDoubleOrder(user.getId() , goodsId);
        if(null != seckillOrder){
            model.addAttribute("errmsg" , RespBeanEnum.REPEAT_ERROR.getMessage());
            return "login";
        }
        GoodsVo goods = goodsService.getGoodsDetailById(goodsId);
        Order order = orderService.secKill(user , goods);
        model.addAttribute("order" , order);
        model.addAttribute("goods" , goods);
        return "orderDetail";




    }
}
