package com.example.demo.controller;

import com.example.demo.service.ImageService;
import com.example.demo.service.ImageUploader;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Controller
public class UploadImageGallery {
    private final ImageService imageService;
    private final ImageUploader imageUploader;
    private final UserService userService;
    //public static String uploadDirectory = System.getProperty("user.dir") + "/uploads/images";


    @Autowired
    public UploadImageGallery(ImageService imageService,
                              ImageUploader imageUploader, UserService userService) {
        this.imageService = imageService;
        this.imageUploader = imageUploader;
        this.userService = userService;
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        if (userService.loggedUserIsAdminChecker()) {
            model.addAttribute("admin", "Admin");
        }
        return "uploadview";
    }


    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
        if (userService.loggedUserIsAdminChecker()) {
            model.addAttribute("admin", "Admin");
        }
        if(files.length > 10) {
            model.addAttribute("msg", "Failed uploaded files, max number of files is 10");
            return "uploadview";
        }

        try {
            StringBuilder fileNames = new StringBuilder();
            StringBuilder result = new StringBuilder();
            for (MultipartFile file : files) {
                fileNames.append(file.getOriginalFilename() + "       ");
                try {

                    imageUploader.uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("msg", "Failed uploaded files: " + fileNames.toString() + "  ");
                }
            }
            model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString() + "  ");
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("msg", "Failed uploaded files: " + "  (" + ex + ")");
            return "uploadview";
        }
        return "uploadview";
    }

}
