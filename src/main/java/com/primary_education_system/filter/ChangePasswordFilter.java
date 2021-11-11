package com.primary_education_system.filter;

import com.primary_education_system.config.security.AuthenticatedUserInfoHolder;
import com.primary_education_system.config.security.AuthenticationUtils;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ChangePasswordFilter implements Filter {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserInfoHolder userInfoHolder;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (AuthenticationUtils.isAuthenticated()) {

        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) {
    }

}
