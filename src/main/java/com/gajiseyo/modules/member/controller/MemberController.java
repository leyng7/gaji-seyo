package com.gajiseyo.modules.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/members/form")
    public String form() {

        return "Hello";
    }

}
