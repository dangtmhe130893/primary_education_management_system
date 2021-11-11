package com.primary_education_system.service;

import com.primary_education_system.config.security.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
public class RedirectService {


    public String getDefaultRedirectUri(CustomUserDetails userDetails) {
        String uri = null;
        return uri;
    }
}
