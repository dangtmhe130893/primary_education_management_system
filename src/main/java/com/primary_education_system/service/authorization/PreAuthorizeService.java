package com.primary_education_system.service.authorization;

import com.primary_education_system.config.security.AuthenticatedUserInfoHolder;
import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.Roles;
import com.primary_education_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service(value = "authorizationService")
public class PreAuthorizeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserInfoHolder userInfoHolder;

    public boolean hasPermissionAccess(String roleCanAccess) {
        CustomUserDetails userDetails = userInfoHolder.getUserDetails();
        if (userDetails == null) {
            return false;
        }
        boolean canAccess = false;
        UserEntity userEntity = userRepository.findOne(userDetails.getUserId());
        Set<RoleEntity> roles = userEntity.getRoles();
        for (RoleEntity role : roles) {
            String roleName = role.getName();
            if (Roles.SYSTEM_ADMIN.name().equals(roleName)) {
                canAccess = true;
            }
            if (roleCanAccess.equals(roleName)) {
                canAccess = true;
            }
        }
        return canAccess;
    }

    public boolean isPupil() {
        CustomUserDetails userDetails = userInfoHolder.getUserDetails();
        if (userDetails == null) {
            return false;
        }
        return "PUPIL".equals(userDetails.getUserType());
    }

}
