package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.LoginRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.LoginResponse;
import com.dgmoonlabs.psythinktank.domain.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.LOGIN;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.ROOT;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String getLoginForm() {
        return LOGIN.getText();
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        LoginResponse loginResponse = loginService.login(loginRequest, session);
        if (loginResponse.isSucceeded()) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_KEY.getText());
        return ROOT.redirect();
    }
}
