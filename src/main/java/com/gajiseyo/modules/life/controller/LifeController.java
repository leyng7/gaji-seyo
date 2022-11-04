package com.gajiseyo.modules.life.controller;

import com.gajiseyo.modules.life.service.LifeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LifeController {

    private final LifeService lifeService;

    @GetMapping("/lives")
    public String list(Model model) {

        model.addAttribute("list", lifeService.findAll());
        return "user/life/list";
    }

}
