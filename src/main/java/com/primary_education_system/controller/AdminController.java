package com.primary_education_system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String getHome() {
        return "admin/home";
    }

    @GetMapping("/account")
    public String getAccount() {
        return "admin/account";
    }

    @GetMapping("/class")
    public String getClassRoom() {
        return "admin/class";
    }

    @GetMapping("/time_schedule")
    public String getTimeSchedule() {
        return "admin/time_schedule";
    }

}
