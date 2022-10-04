package com.gajiseyo.modules.myInfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyInfoController {

    @GetMapping("/myInfo")
    public String view() {

        return "user/myInfo/view";
    }

}
