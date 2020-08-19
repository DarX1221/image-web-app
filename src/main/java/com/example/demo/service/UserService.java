package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.model.RegisterUserTemplate;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(AppUser appUser) {
        userRepository.save(appUser);
    }

    public String checkUserTemplate(RegisterUserTemplate newUser) {
        if(newUser.getUsername() == null || newUser.getEmail() == null){
            return "Please complete all fields!";
        }
        if(userRepository.findByUsername(newUser.getUsername()) != null){
            return "This username is occupied";
        }
        if(!newUser.checkEmail()){
            return "Invalid email!";
        }
        if(userRepository.findAppUserByEmail(newUser.getEmail()) != null){
            return "This email is occupied";
        }
        if(!newUser.checkPasswords()){
            return "Passwords are different!";
        }
        return null;
    }

    public boolean checkTahtLoggedUserIsAdmin() {
        // take username from SpringSecurity and check this user in DB
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(userRepository.findByUsername(username) == null) {
            return false;
        }
        if(userRepository.findByUsername(username).getRole().equals("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    public AppUser registerUserTemplateToAppUser(RegisterUserTemplate templateUser) {
        return new AppUser(templateUser.getUsername(),
                templateUser.getEmail(), templateUser.getPassword(), "ROLE_USER");
    }

}
