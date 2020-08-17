package com.example.demo.service;

import com.example.demo.model.AppImage;
import com.example.demo.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageUploader imageUploader;
    private final UserService userService;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        ImageUploader imageUploader, UserService userService) {
        this.imageRepository = imageRepository;
        this.imageUploader = imageUploader;
        this.userService = userService;
    }

    public List<AppImage> getAllImages() {
        //check admin role
        if (userService.loggedUserIsAdminChecker()) {
            return imageRepository.findAll();
        }
        return null;
    }


    public List<AppImage> getImagesByUsername(String username) {
        return imageRepository.findByAppUser_Username(username);
    }

    public boolean removeImageById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        // if logged username is different than username in AppImage(Long id) to delete, you can delete image only if you are admin
        if (!imageRepository.findAppImageById(id).getAppUser().getUsername().equals(username)) {
            if (!userService.loggedUserIsAdminChecker()) {
                return false;
            }
        }

        // double click "delete image", try to remove image two times
        try {
            String cloudinaryId = imageRepository.findAppImageById(id).getCloudinaryId();
            imageUploader.deleteImage(cloudinaryId);
            imageRepository.removeAppImageById(id);
            return true;
        } catch (Exception ex) {}
        return false;
    }

}
