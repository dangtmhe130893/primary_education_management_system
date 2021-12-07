package com.primary_education_system.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class CustomUserDetails extends User {
    private Long userId;
    private String role;
    private String accessToken;
    private String refreshToken;
    private String redirectUrl;
    private Date timestamp;
    private Integer year;
    private Integer month;
    private Integer day;
    private String userType;


    public CustomUserDetails(String username, String password, Long userId, String userType, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.userType = userType;
    }
}
