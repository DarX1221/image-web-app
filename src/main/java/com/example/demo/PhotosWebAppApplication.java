package com.example.demo;

import java.io.File;

import com.example.demo.controller.UploadImageGallery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.example.demo", "com.example.demo.controller"})
public class PhotosWebAppApplication {


    public static void main(String[] args) {
        //new File(UploadImageGallery.uploadDirectory).mkdir();
        SpringApplication.run(PhotosWebAppApplication.class, args);
    }

}
