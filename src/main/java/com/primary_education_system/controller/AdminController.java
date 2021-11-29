package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.material.MaterialEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.service.*;
import com.primary_education_system.service.material.MaterialService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClassService classService;

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private PupilAccountService pupilAccountService;

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
        List<SubjectEntity> listSubject = subjectService.getListByUserId(currentUser.getUserId());
        if (listSubject == null) {
            return "/login";
        }
        model.addAttribute("listSubject", listSubject);
        return "admin/material";
    }

    @GetMapping("/time_schedule_teacher")
    public String getTeachClass(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "admin/time_schedule_teacher";
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

    @GetMapping("/teach_class/{seoNameClass}")
    public String getDetailTeachClass(@PathVariable String seoNameClass, Model model,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ClassEntity classEntity = classService.getBySeo(seoNameClass);
        String grade = classEntity.getGrade();
        model.addAttribute("classs", classEntity);
        model.addAttribute("grade", classEntity.getGrade());

        UserEntity userEntity = userService.findByIdAndIsDeletedFalse(customUserDetails.getUserId());

        return "admin/teach_class";
    }
    @GetMapping("/tuition")
    public String getTuition() {
        return "admin/tuition";
    }


    @GetMapping("/room")
    public String getRoom() {
        return "admin/room";
    }

    @GetMapping("/my_class")
    public String myClass(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        ClassEntity classEntity = classService.findByHomeroomTeacherId(customUserDetails.getUserId());

        if (classEntity != null) {
            int numberPupilInClass = pupilAccountService.countNumberPupilInClass(classEntity.getId());
            model.addAttribute("class", classEntity);
            model.addAttribute("numberPupil", numberPupilInClass);
        } else {
            model.addAttribute("class", new ClassEntity(""));
        }
        return "admin/my_class";
    }

}
