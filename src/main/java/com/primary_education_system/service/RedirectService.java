package com.primary_education_system.service;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.Roles;
import com.primary_education_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedirectService {

    @Autowired
    private UserRepository userRepository;

    public String getDefaultRedirectUri(CustomUserDetails currentUser) {
        boolean isSystem = false;
        boolean isStaff = false;
        boolean isTeacher = false;
        boolean isAcademicHead = false;
        boolean isHeadMaster = false;
        boolean isPupilParent = false;
        UserEntity userEntity = userRepository.findOne(currentUser.getUserId());
        Set<RoleEntity> roles = userEntity.getRoles();
        for (RoleEntity role : roles) {
            String roleName = role.getName();
            if (Roles.SYSTEM_ADMIN.name().equals(roleName)) {
                isSystem = true;
            }
            if (Roles.STAFF.name().equals(roleName)) {
                isStaff = true;
            }
            if (Roles.TEACHER.name().equals(roleName)) {
                isTeacher = true;
            }
            if (Roles.ACADEMIC_HEAD.name().equals(roleName)) {
                isAcademicHead = true;
            }
            if (Roles.HEAD_MASTER.name().equals(roleName)) {
                isHeadMaster = true;
            }
            if (Roles.PUPIL_PARENT.name().equals(roleName)) {
                isPupilParent = true;
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
        if (isPupilParent) {
            return "/home";
        }
        return "/home";
    }
}
