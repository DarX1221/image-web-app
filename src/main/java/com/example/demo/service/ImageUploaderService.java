package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.AppImage;
import com.example.demo.model.AppUser;
import com.example.demo.repo.ImageRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploaderService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Autowired
    public ImageUploaderService(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
        // configuration (Map) for image cloud (cloudinary)
        Map config = new HashMap();
        config.put("cloud_name", "dhm3ydouf");
        config.put("api_key", "738384397126113");
        config.put("api_secret", "R5eHqWGKwBxa61HC0TuKqatm3DQ");
        this.cloudinary = new Cloudinary(config);
    }

    /***
     * Uploads the image from client(user) to app server, and then uploads on cloud and saves in the DB
     * @throws IOException if the uploads on server or cloud will failed
     */
    public void uploadImage(MultipartFile mFile) throws IOException {
        File file = new File("filename");
        FileUtils.writeByteArrayToFile(file, mFile.getBytes());
        if( ImageIO.read(file) == null) {throw new IOException();}
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("secure_url");
        String cloudinaryId = (String) uploadResult.get("public_id");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser appUser = userService.findByUsername(username);
        imageRepository.save(new AppImage(cloudinaryId, imageUrl, appUser));
    }

    public boolean deleteImage(String cloudinaryId) {
        try {
            cloudinary.uploader().destroy(cloudinaryId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}