package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.service.ClassService;
import com.primary_education_system.service.FrameTimeScheduleService;
import com.primary_education_system.service.PupilAccountService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private ClassService classService;

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

    @GetMapping("/material")
    public String getMaterial(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                              Model model) {
        Long pupilId = customUserDetails.getUserId();
        PupilAccountEntity pupilEntity = pupilAccountService.findByIdAndIsDeletedFalse(pupilId);

        Long classId = pupilEntity.getClassId();
        String className = classService.getClassNameByClassId(classId);

        model.addAttribute("className", className);
        model.addAttribute("classId", classId);

        return "pupil/pupil_material";
    }
}
