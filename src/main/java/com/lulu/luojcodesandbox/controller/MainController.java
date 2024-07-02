package com.lulu.luojcodesandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lulu
 * @version 1.0
 * @description TODO
 * @date 2024/7/2 13:56
 */
@RestController("/")
public class MainController {

    @GetMapping("/health")
    public String healthCheck(){
        return "Health Check OK!";
    }

}
