package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private PupilAccountService pupilAccountService;

    @GetMapping("/home")
    public String getHome() {
        return "admin/home";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('SYSTEM_ADMIN')")
    @GetMapping("/account")
    public String getAccount() {
        return "admin/account";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('STAFF')")
    @GetMapping("/tuition_status")
    public String getTuitionStatus() {
        return "admin/tuition_status/tuition_status";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('SYSTEM_ADMIN')")
    @GetMapping("/pupil_account")
    public String getPupilAccount() {
        return "admin/pupil_account";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('TEACHER')")
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

    @PreAuthorize("@authorizationService.hasPermissionAccess('TEACHER')")
    @GetMapping("/time_schedule_teacher")
    public String getTeachClass(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "admin/time_schedule_teacher";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('ACADEMIC_HEAD')")
    @GetMapping("/class")
    public String getClassRoom() {
        return "admin/class";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('ACADEMIC_HEAD')")
    @GetMapping("/time_schedule")
    public String getTimeSchedule(Model model) {
        List<FrameTimeScheduleEntity> listFrame = frameTimeScheduleService.findAll();
        model.addAttribute("listFrame", listFrame);
        return "admin/time_schedule";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('ACADEMIC_HEAD')")
    @GetMapping("/subject")
    public String getSubject() {
        return "admin/subject";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('TEACHER')")
    @GetMapping("/teach_class/{seoNameClass}")
    public String getDetailTeachClass(@PathVariable String seoNameClass, Model model,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ClassEntity classEntity = classService.getBySeo(seoNameClass);
        model.addAttribute("classs", classEntity);
        model.addAttribute("grade", classEntity.getGrade());

        UserEntity userEntity = userService.findByIdAndIsDeletedFalse(customUserDetails.getUserId());

        return "admin/teach_class";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('HEAD_MASTER')")
    @GetMapping("/tuition")
    public String getTuition() {
        return "admin/tuition";
    }

    @PreAuthorize("@authorizationService.hasPermissionAccess('ACADEMIC_HEAD')")
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
            model.addAttribute("numberPupil", 0);
        }
        return "admin/my_class";
    }

}
