package com.example.demo.model;

import javax.persistence.*;


@Entity
public class AppImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // cloudinaryId is public_id use on cloudinary.com (need to delete image)
    private String cloudinaryId;
    private String adress;
    @ManyToOne
    private AppUser appUser;

    public AppImage() {
    }

    public AppImage(String cloudinaryId, String address, AppUser appUser) {
        this.cloudinaryId = cloudinaryId;
        this.adress = address;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }
}
