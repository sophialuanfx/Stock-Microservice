package com.stock.microservice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/login")
    public String loginpage() {
        return "loginpage"; // 返回的字符串是templates文件夹中的filename.html
    }


    @GetMapping("/home")
    public String homepage() {
        return "homepage";
    }
}
