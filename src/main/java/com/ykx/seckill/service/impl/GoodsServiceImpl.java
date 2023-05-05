package com.ykx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ykx.seckill.mapper.GoodsMapper;
import com.ykx.seckill.pojo.Goods;
import com.ykx.seckill.service.IGoodsService;
import com.ykx.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-05-05
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public List<GoodsVo> selectAllGoodsVo() {
        return this.baseMapper.selectAllGoodsVo();

    }

    @Override
    public GoodsVo getGoodsDetailById(Long goodsId) {
        return this.baseMapper.selectGoodsDetailById(goodsId);
    }

    @Override
    public boolean checkShockEnough(Long goodsId) {
        Goods one = this.getOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, goodsId));
        if(one.getGoodsStock() < 1 && one.getGoodsStock() != -1){
            return true;
        }
        return false;


    }
}
