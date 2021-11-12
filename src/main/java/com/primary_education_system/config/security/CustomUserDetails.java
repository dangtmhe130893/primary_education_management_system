package com.primary_education_system.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class CustomUserDetails extends User {
    private Long userId;
    private String role;
    private Long appId;
    private boolean isSystemAdmin;
    private boolean isWebUser;
    private String appIdString;
    private Long companyId;
    private String accessToken;
    private String refreshToken;
    private String redirectUrl;
    private Date timestamp;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minutes;


    public CustomUserDetails(String username, String password, Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
