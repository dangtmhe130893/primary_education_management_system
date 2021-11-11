package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserInfoHeaderController {
    @Autowired
    private UserService userService;

    @ModelAttribute()
    public void getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    }
}
