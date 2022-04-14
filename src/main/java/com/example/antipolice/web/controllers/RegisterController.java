package com.example.antipolice.web.controllers;

import com.example.antipolice.model.exceptions.InvalidArgumentsException;
import com.example.antipolice.model.exceptions.UnderAgeException;
import com.example.antipolice.model.exceptions.UsernameAlreadyExistsException;
import com.example.antipolice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("body","register");
        model.addAttribute("hasError", true);
        model.addAttribute("error",error);
        return "base";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam int age) {
        try{
            this.userService.register(username, password, age);
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException | InvalidArgumentsException | UnderAgeException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
