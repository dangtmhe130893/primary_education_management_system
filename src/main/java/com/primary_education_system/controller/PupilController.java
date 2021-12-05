package com.primary_education_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pupil")
public class PupilController {

    @GetMapping("/home")
    public String getHome() {
        return "pupil/home";
    }

}
