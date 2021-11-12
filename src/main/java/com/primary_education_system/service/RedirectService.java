package com.primary_education_system.service;

import com.google.common.base.Objects;
import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.enum_type.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RedirectService {

    public String getDefaultRedirectUri(CustomUserDetails userDetails) {
        String uri;
        boolean isPupilParent = false;
        Collection<GrantedAuthority> auths = userDetails.getAuthorities();
        for (GrantedAuthority auth : auths) {
            if (Objects.equal(auth.getAuthority(), Roles.PUPIL_PARENT.name())) {
                isPupilParent = true;
                break;
            }
        }
        if (isPupilParent) {
            uri = "/pupil_parent/";
        } else {
            uri = "/admin/account";
        }
        return uri;
    }
}
