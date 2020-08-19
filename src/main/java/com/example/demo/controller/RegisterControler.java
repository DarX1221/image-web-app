package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.RegisterUserTemplate;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterControler {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterControler(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String register(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (username.equals("anonymousUser") || username == null) {
            model.addAttribute("newUser", new RegisterUserTemplate());
            return "register";
        } else {
            return "redirect:/gallery";
        }//if user is logged, redirect to gallery
    }

    @PostMapping("/")
    public String newUser(@ModelAttribute RegisterUserTemplate newUser, Model model) {
        String validationMsg = userService.checkUserTemplate(newUser);
        if (validationMsg != null) {
            model.addAttribute("newUser", new RegisterUserTemplate());
            model.addAttribute("msg", validationMsg);
            return "register";
        }
        model.addAttribute("newUser", new RegisterUserTemplate());
        model.addAttribute("msgpass", "Registration succesful!");
        String pass = passwordEncoder.encode(newUser.password);
        newUser.setPassword(pass);
        newUser.setPasswordRepeat(pass);
        AppUser appUser = userService.registerUserTemplateToAppUser(newUser);
        userService.saveUser(appUser);
        return "register";
    }
}
