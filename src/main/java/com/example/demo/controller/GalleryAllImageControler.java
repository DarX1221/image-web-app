package com.example.demo.controller;

import com.example.demo.model.AppImage;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class GalleryAllImageControler {
    private final ImageService imageService;


    @Autowired
    public GalleryAllImageControler(ImageService imageService) {
        this.imageService = imageService;
    }

    // admin gallery returns images of all users
    @GetMapping("/all_images")
    String adminGallery(Model model) {
        List<AppImage> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "admin-gallery";
    }

    @RequestMapping(value = "/deleteadm", method = RequestMethod.POST)
    public String handleDeleteImageAdm(@RequestParam String id) {
        Long idL = Long.valueOf(id);
        imageService.removeImageById(idL);
        return "redirect:/all_images";
    }


}
