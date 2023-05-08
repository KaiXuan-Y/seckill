package com.ykx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ykx.seckill.mapper.OrderMapper;
import com.ykx.seckill.pojo.Order;
import com.ykx.seckill.pojo.SeckillGoods;
import com.ykx.seckill.pojo.SeckillOrder;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.service.IOrderService;
import com.ykx.seckill.service.ISeckillGoodsService;
import com.ykx.seckill.service.ISeckillOrderService;
import com.ykx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {


    @Autowired
    ISeckillGoodsService seckillGoodsService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*
    秒杀
     */
    @Override
    public Order secKill(User user, GoodsVo goods) {
       //1.减秒杀库存
        SeckillGoods seckillGoods = seckillGoodsService.getById(goods.getId());
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1 );
        boolean seckillGoodResult = seckillGoodsService.update(new LambdaUpdateWrapper<SeckillGoods>()
                .setSql("stock_count = " + "stock_count - 1")
                .eq(SeckillGoods::getGoodsId, seckillGoods.getId())
                .gt(SeckillGoods::getStockCount, 0));
        if(!seckillGoodResult){
            return null;
        }

        //2.创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(LocalDateTime.now());
        orderMapper.insert(order);

        //3.创建秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);

        redisTemplate.opsForValue().set("userId:"+user.getId() + "goodsId:"+goods.getId() , seckillOrder);
        return order;


    }
}
