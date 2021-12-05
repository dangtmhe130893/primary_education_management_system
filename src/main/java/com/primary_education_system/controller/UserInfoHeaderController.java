package com.primary_education_system.controller;

import com.google.common.base.Objects;
import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.enum_type.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.Collection;

@ControllerAdvice
public class UserInfoHeaderController {

    @ModelAttribute()
    public void getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                               Model model) throws IOException {
        if (currentUser == null) {
            return;
        }
        boolean isCanShowSystemMenu = false;
        boolean isCanShowStaffMenu = false;
        boolean isCanShowTeacherMenu = false;
        boolean isCanShowAcademicHeadMenu = false;
        boolean isCanShowHeadMasterMenu = false;
        boolean isCanShowPupilMenu = false;
        Collection<GrantedAuthority> auths = currentUser.getAuthorities();
        for (GrantedAuthority auth : auths) {
            if (Objects.equal(auth.getAuthority(), Roles.SYSTEM_ADMIN.name())) {
                isCanShowSystemMenu = true;
                isCanShowStaffMenu = true;
                isCanShowTeacherMenu = true;
                isCanShowAcademicHeadMenu = true;
                isCanShowHeadMasterMenu = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.STAFF.name())) {
                isCanShowStaffMenu = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.TEACHER.name())) {
                isCanShowTeacherMenu = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.ACADEMIC_HEAD.name())) {
                isCanShowAcademicHeadMenu = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.HEAD_MASTER.name())) {
                isCanShowHeadMasterMenu = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.PUPIL_PARENT.name())) {
                isCanShowPupilMenu = true;
            }
        }

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("year", currentUser.getYear());
        model.addAttribute("month", currentUser.getMonth());
        model.addAttribute("day", currentUser.getDay());

        model.addAttribute("isCanShowSystemMenu", isCanShowSystemMenu);
        model.addAttribute("isCanShowStaffMenu", isCanShowStaffMenu);
        model.addAttribute("isCanShowTeacherMenu", isCanShowTeacherMenu);
        model.addAttribute("isCanShowAcademicHeadMenu", isCanShowAcademicHeadMenu);
        model.addAttribute("isCanShowHeadMasterMenu", isCanShowHeadMasterMenu);
        model.addAttribute("isCanShowPupilMenu", isCanShowPupilMenu);

    }
}
