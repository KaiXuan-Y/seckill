package com.ykx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ykx.seckill.pojo.Goods;
import com.ykx.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> selectAllGoodsVo();

    GoodsVo getGoodsDetailById(Long goodsId);

    boolean checkShockEnough(Long goodsId);
}
