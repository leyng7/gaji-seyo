package com.gajiseyo.modules.myInfo.controller;

import com.gajiseyo.modules.member.auth.CurrentUser;
import com.gajiseyo.modules.member.domain.Member;
import com.gajiseyo.modules.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MyInfoController {

    private final MemberService memberService;

    @GetMapping("/myInfo")
    public String view(@CurrentUser Member currentUser,
                       Model model) {

        model.addAttribute("currentUser", currentUser);

        return "user/myInfo/view";
    }

    @GetMapping("/myInfo/edit")
    public String edit(@CurrentUser Member currentUser,
                       Model model) {

        model.addAttribute("currentUser", currentUser);

        return "user/myInfo/edit";
    }

    @PostMapping("/myInfo/edit")
    public String update(@CurrentUser Member currentUser,
                         @RequestParam String nickname) {

        memberService.updateNickname(currentUser, nickname);

        return "redirect:/myInfo";
    }

}
