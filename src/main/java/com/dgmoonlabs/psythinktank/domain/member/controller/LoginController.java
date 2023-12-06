package com.dgmoonlabs.psythinktank.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.LOGIN;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String getLoginForm() {
        return LOGIN.getText();
    }
}
