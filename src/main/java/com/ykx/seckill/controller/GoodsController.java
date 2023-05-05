package com.ykx.seckill.controller;

import com.ykx.seckill.pojo.User;
import com.ykx.seckill.service.IGoodsService;
import com.ykx.seckill.service.IUserService;
import com.ykx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@RequestMapping("/goods")
@Controller
public class GoodsController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;

    @RequestMapping("/toList")
    public String toList(Model model, User user) {
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        User user = userService.getUserByCookie(ticket, request, response);
//        if(null == user){
//            return "login";
//        }
        model.addAttribute("user", user);
        List<GoodsVo> goodsVoList = goodsService.selectAllGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        return "goodsList";
    }

    @RequestMapping("/toDetail")
    public String toDetail(Model model, @RequestParam("goodsId") Long goodsId, User user) {
        int secKillStatus = 0;
        int remainSeconds = 0;
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.getGoodsDetailById(goodsId);

        LocalDateTime startDate = goodsVo.getStartDate();
        LocalDateTime endDate = goodsVo.getEndDate();

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDate)) {
            //秒杀还没有开始
            Duration duration = Duration.between(now, startDate);
            remainSeconds = (int) (duration.toMillis() / 1000);
//            remainSecKill = startDate.getSecond() - now.getSecond();
        } else if (now.isAfter(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
        }

        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);

        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}
