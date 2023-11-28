package com.mdg.PSYThinktank.member.controller;

import com.mdg.PSYThinktank.member.service.LoginService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login2(@RequestBody HashMap<String, String> map, HttpSession session, Model model) {
        return loginService.login(map.get("memberId"), map.get("memberPw"), session, model);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("member");
        return "redirect:/";
    }
}
