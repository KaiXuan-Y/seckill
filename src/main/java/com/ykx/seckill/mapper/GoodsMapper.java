package com.ykx.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ykx.seckill.pojo.Goods;
import com.ykx.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> selectAllGoodsVo();

    GoodsVo selectGoodsDetailById(@Param("goodsId") Long goodsId);
}
