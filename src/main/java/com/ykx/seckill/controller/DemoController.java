package com.ykx.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2023/5/4.
 *
 * @author KaiXuan Yang
 */
@Controller
@RequestMapping("/hello")
public class DemoController {
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","xxx");
        return "hello";
    }

}
