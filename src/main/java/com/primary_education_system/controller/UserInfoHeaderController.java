package com.primary_education_system.controller;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.Roles;
import com.primary_education_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@ControllerAdvice
public class UserInfoHeaderController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute()
    public void getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                               Model model, HttpServletResponse response) throws IOException {
        if (currentUser == null) {
            response.sendRedirect("/login");
            return;
        }
        UserEntity userEntity = userRepository.findOne(currentUser.getUserId());
        Set<RoleEntity> roles = userEntity.getRoles();

        boolean isCanShowSystemMenu = false;
        boolean isCanShowStaffMenu = false;
        boolean isCanShowTeacherMenu = false;
        boolean isCanShowAcademicHeadMenu = false;
        boolean isCanShowHeadMasterMenu = false;

        for (RoleEntity role : roles) {
            String roleName = role.getName();
            if (Roles.SYSTEM_ADMIN.name().equals(roleName)) {
                isCanShowSystemMenu = true;
                isCanShowStaffMenu = true;
                isCanShowTeacherMenu = true;
                isCanShowAcademicHeadMenu = true;
                isCanShowHeadMasterMenu = true;
            }
            if (Roles.STAFF.name().equals(roleName)) {
                isCanShowStaffMenu = true;
            }
            if (Roles.TEACHER.name().equals(roleName)) {
                isCanShowTeacherMenu = true;
            }
            if (Roles.ACADEMIC_HEAD.name().equals(roleName)) {
                isCanShowAcademicHeadMenu = true;
            }
            if (Roles.HEAD_MASTER.name().equals(roleName)) {
                isCanShowHeadMasterMenu = true;
            }
        }

        model.addAttribute("user", userEntity);
        model.addAttribute("year", currentUser.getYear());
        model.addAttribute("month", currentUser.getMonth());
        model.addAttribute("day", currentUser.getDay());

        model.addAttribute("isCanShowSystemMenu", isCanShowSystemMenu);
        model.addAttribute("isCanShowStaffMenu", isCanShowStaffMenu);
        model.addAttribute("isCanShowTeacherMenu", isCanShowTeacherMenu);
        model.addAttribute("isCanShowAcademicHeadMenu", isCanShowAcademicHeadMenu);
        model.addAttribute("isCanShowHeadMasterMenu", isCanShowHeadMasterMenu);

    }
}
