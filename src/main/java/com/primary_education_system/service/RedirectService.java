package com.primary_education_system.service;

import com.google.common.base.Objects;
import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.enum_type.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RedirectService {

    public String getDefaultRedirectUri(CustomUserDetails currentUser) {
        boolean isSystem = false;
        boolean isStaff = false;
        boolean isTeacher = false;
        boolean isAcademicHead = false;
        boolean isHeadMaster = false;
        boolean isPupil = false;

        Collection<GrantedAuthority> auths = currentUser.getAuthorities();
        for (GrantedAuthority auth : auths) {
            if (Objects.equal(auth.getAuthority(), Roles.SYSTEM_ADMIN.name())) {
                isSystem = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.STAFF.name())) {
                isStaff = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.TEACHER.name())) {
                isTeacher = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.ACADEMIC_HEAD.name())) {
                isAcademicHead = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.HEAD_MASTER.name())) {
                isHeadMaster = true;
            }
            if (Objects.equal(auth.getAuthority(), Roles.PUPIL_PARENT.name())) {
                isPupil = true;
            }
        }
        if (isSystem) {
            return "/admin/account";
        }
        if (isStaff) {
            return "/admin/tuition_status";
        }
        if (isTeacher) {
            return "/admin/time_schedule_teacher";
        }
        if (isAcademicHead) {
            return "/admin/time_schedule";
        }
        if (isHeadMaster) {
            return "/admin/tuition";
        }
        if (isPupil) {
            return "/pupil/home";
        }
        return "/home";
    }
}
