package com.primary_education_system.config.security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticatedUserInfoHolder {

    private CustomUserDetails userDetails;

    @PostConstruct
    public void init() {
        if (!AuthenticationUtils.isAuthenticated()) {
            this.userDetails = null;
            return;
        }
        this.userDetails = (CustomUserDetails) AuthenticationUtils.getAuthentication().getPrincipal();
    }

    public CustomUserDetails getUserDetails() {
        return userDetails;
    }

}
