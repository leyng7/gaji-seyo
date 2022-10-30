package com.gajiseyo.modules.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/items";
    }

    @GetMapping("/login")
    public String login() {
        return "user/member/login";
    }

}
