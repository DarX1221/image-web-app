package com.example.demo.repo;

import com.example.demo.model.AppImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<AppImage, Long> {

    List<AppImage> findByAppUser_Id(Long appUserId);
    List<AppImage> findByAppUser_Username(String username);

    List<AppImage> findAll();
    AppImage findAppImageById(Long id);

    @Transactional
    void removeAppImageById(Long id);
}
