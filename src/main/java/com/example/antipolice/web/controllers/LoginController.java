package com.example.antipolice.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {


    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("body","login");
        model.addAttribute("hasError", true);
        model.addAttribute("error",error);
        return "base";
    }

}
