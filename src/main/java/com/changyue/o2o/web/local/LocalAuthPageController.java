package com.changyue.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: o2o
 * @description: 平台页面控制器
 * @author: YuanChangYue
 * @create: 2019-09-25 20:30
 */
@Controller
@RequestMapping("local")
public class LocalAuthPageController {

    @GetMapping("/login")
    public String login() {
        return "local/login";
    }

}
