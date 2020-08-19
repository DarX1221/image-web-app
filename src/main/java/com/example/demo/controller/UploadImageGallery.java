package com.example.demo.controller;

import com.example.demo.service.ImageUploaderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadImageGallery {
    private final ImageUploaderService imageUploaderService;
    private final UserService userService;

    @Autowired
    public UploadImageGallery(ImageUploaderService imageUploaderService, UserService userService) {
        this.imageUploaderService = imageUploaderService;
        this.userService = userService;
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        if (userService.checkTahtLoggedUserIsAdmin()) {
            model.addAttribute("admin", "Admin");
        }
        return "uploadview";
    }


    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
        if (userService.checkTahtLoggedUserIsAdmin()) {
            model.addAttribute("admin", "Admin");
        }
        if (files.length > 10) {
            model.addAttribute("msg", "Failed uploaded files, max number of files is 10");
            return "uploadview";
        }

        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            try {
                imageUploaderService.uploadImage(file);
                fileNames.append(file.getOriginalFilename() + ",   ");
            } catch (Exception ex) {
                ex.printStackTrace();
                model.addAttribute("msg", "Failed uploaded files:  " + fileNames.toString());
                return "uploadview";
            }
            model.addAttribute("msg", "Successful uploaded files:  " + fileNames.toString());

        }
        return "uploadview";
    }

}