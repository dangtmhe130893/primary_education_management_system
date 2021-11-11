package com.primary_education_system.service.authorization;

import com.primary_education_system.config.security.AuthenticatedUserInfoHolder;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "authorizationService")
public class PreAuthorizeService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserInfoHolder userInfoHolder;

}
