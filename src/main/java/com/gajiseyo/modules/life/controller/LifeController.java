package com.gajiseyo.modules.life.controller;

import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.dto.LifeForm;
import com.gajiseyo.modules.life.service.LifeService;
import com.gajiseyo.modules.member.auth.CurrentUser;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LifeController {

    private final LifeService lifeService;

    @GetMapping("/lives")
    public String list(Model model) {

        model.addAttribute("list", lifeService.findAll());
        return "user/life/list";
    }

    @GetMapping("/lives/form")
    public String form(@ModelAttribute("form") LifeForm form,
                       Model model) {

        model.addAttribute("form", form);
        return "user/life/form";
    }

    @PostMapping("/lives/form")
    public String insert(@CurrentUser Member currentUser,
                         @ModelAttribute("form") LifeForm form,
                         RedirectAttributes rttr) {

        lifeService.insert(form, currentUser);

        rttr.addFlashAttribute("message", "슬기로운 가지생활이 정상적으로 등록되었습니다");
        return "redirect:/lives";
    }

    @GetMapping("/lives/{id}")
    public String view(@PathVariable("id") Long lifeId,
                       @CurrentUser Member currentUser,
                       Model model) {
        Life life = lifeService.findById(lifeId).orElseThrow();
        model.addAttribute("life", life);

        return "/user/life/view";
    }

}
