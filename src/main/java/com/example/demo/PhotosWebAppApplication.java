package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.example.demo", "com.example.demo.controller"})
public class PhotosWebAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(PhotosWebAppApplication.class, args);
    }

}
