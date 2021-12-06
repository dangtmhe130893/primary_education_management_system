package com.primary_education_system.controller;

import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.service.FrameTimeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pupil")
@PreAuthorize("@authorizationService.isPupil()")
public class PupilController {

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    @GetMapping("/home")
    public String getHome() {
        return "pupil/home";
    }

    @GetMapping("/tuition")
    public String getTuition() {
        return "pupil/tuition";
    }

    @GetMapping("/time_table")
    public String getTimeTable(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "pupil/time_table";
    }

}
