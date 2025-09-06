package com.unifil.advocacia.gerenciador.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home"; 
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; 
    }
}