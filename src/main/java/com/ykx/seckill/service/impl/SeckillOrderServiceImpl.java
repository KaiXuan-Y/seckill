package com.ykx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ykx.seckill.mapper.SeckillOrderMapper;
import com.ykx.seckill.pojo.SeckillOrder;
import com.ykx.seckill.service.ISeckillOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Override
    public SeckillOrder checkDoubleOrder(Long userId, Long goodsId) {
        SeckillOrder one = this.getOne(new LambdaQueryWrapper<SeckillOrder>().eq(SeckillOrder::getUserId, userId)
                .eq(SeckillOrder::getGoodsId, goodsId));
        return one;


    }
}
