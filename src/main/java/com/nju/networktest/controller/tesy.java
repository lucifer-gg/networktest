package com.nju.networktest.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tesy {
    @GetMapping("/test")
    public String test1(@Param("tstring") String ss){

        return ss;
    }
}
