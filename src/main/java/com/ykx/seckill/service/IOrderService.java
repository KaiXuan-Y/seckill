package com.ykx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ykx.seckill.pojo.Order;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
public interface IOrderService extends IService<Order> {

    Order secKill(User user, GoodsVo goods);
}
