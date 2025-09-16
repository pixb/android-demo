package com.example.demo.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
/**
 * @author sunql
 * @description controller层
 * @date 2020/12/29 15:30
 */
@Controller
@RequestMapping(value = "/hello")
public class TestController {
 
    @RequestMapping(value = "/test")
    public String test(){
        return "index";
    }
}