package com.primary_education_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pupil")
@PreAuthorize("@authorizationService.isPupil()")
public class PupilController {

    @GetMapping("/home")
    public String getHome() {
        return "pupil/home";
    }

    @GetMapping("/tuition")
    public String getTuition() {
        return "pupil/tuition";
    }

}
