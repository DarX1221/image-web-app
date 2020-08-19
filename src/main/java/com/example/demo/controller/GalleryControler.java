package com.example.demo.controller;

import com.example.demo.model.AppImage;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GalleryControler {
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public GalleryControler(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/gallery")
    String gallery(Model model) {
        if (userService.checkTahtLoggedUserIsAdmin()) {
            model.addAttribute("admin", "Admin");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<AppImage> images = imageService.getImagesByUsername(username);
        model.addAttribute("images", images);
        return "gallery";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String handleDeleteImage(@RequestParam String id) {
        Long idL = Long.valueOf(id);
        imageService.removeImageById(idL);
        return "redirect:/gallery";
    }

}
