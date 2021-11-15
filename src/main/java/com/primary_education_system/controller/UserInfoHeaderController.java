package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserInfoHeaderController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute()
    public void getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
        if (currentUser == null) {
            return;
        }
        model.addAttribute("user", userRepository.findOne(currentUser.getUserId()));
        model.addAttribute("year", currentUser.getYear());
        model.addAttribute("month", currentUser.getMonth());
        model.addAttribute("day", currentUser.getDay());
        model.addAttribute("hour", currentUser.getHour());
        model.addAttribute("minute", currentUser.getMinutes());

    }
}
