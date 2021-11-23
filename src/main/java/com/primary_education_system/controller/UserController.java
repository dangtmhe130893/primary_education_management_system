package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.service.RedirectService;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private RedirectService redirectService;

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String loginForm(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        }
        String uri = redirectService.getDefaultRedirectUri(userDetails);
        return "redirect:" + uri;
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        }
        return "profile";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        return "change_password";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot_password/forgot_password";
    }

    @GetMapping("/set-password")
    public String setPasswordForm() {
        return "forgot_password/set_password";
    }

    @GetMapping("/server-error")
    public String serverError() {
        return "server-error";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/403")
    public String formNotFound() {
        return "access_denied";
    }

    @GetMapping("/confirmForgotPassword")
    public String confirmForgotPassword(@RequestParam("token") String token, Model model) {
        boolean status = userService.confirmForgotPassword(token);
        if (status) {
            model.addAttribute("token", token);
            return "forgot_password/set_password";
        } else {
            return "confirm_error";
        }
    }
}
