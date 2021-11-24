package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.service.FrameTimeScheduleService;
import com.primary_education_system.service.SubjectService;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    @GetMapping("/home")
    public String getHome() {
        return "admin/home";
    }

    @GetMapping("/account")
    public String getAccount() {
        return "admin/account";
    }

    @GetMapping("/tuition_status")
    public String getTuitionStatus() {
        return "admin/tuition_status";
    }

    @GetMapping("/pupil_account")
    public String getPupilAccount() {
        return "admin/pupil_account";
    }

    @GetMapping("/material")
    public String getMaterial(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
        if (currentUser == null) {
            return "/login";
        }
        List<SubjectEntity> listSubject = subjectService.getListByUser(currentUser.getUserId());
        if (listSubject == null) {
            return "/login";
        }
        model.addAttribute("listSubject", listSubject);
        return "admin/material";
    }

    @GetMapping("/teach_class")
    public String getTeachClass(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "admin/teach_class";
    }

    @GetMapping("/class")
    public String getClassRoom() {
        return "admin/class";
    }

    @GetMapping("/time_schedule")
    public String getTimeSchedule(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "admin/time_schedule";
    }

    @GetMapping("/subject")
    public String getSubject() {
        return "admin/subject";
    }

    @GetMapping("/tuition")
    public String getTuition() {
        return "admin/tuition";
    }

}
