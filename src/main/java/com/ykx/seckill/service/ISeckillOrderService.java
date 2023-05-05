package com.ykx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ykx.seckill.pojo.SeckillOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    SeckillOrder checkDoubleOrder(Long userId, Long goodsId);
}
