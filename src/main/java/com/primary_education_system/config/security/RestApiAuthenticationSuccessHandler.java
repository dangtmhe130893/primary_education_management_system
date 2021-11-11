package com.primary_education_system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestApiAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            responseAuthenticationInfo(authentication, response, null);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            responseAuthenticationInfo(authentication, response, savedRequest);
            return;
        }

        clearAuthenticationAttributes(request);
        responseAuthenticationInfo(authentication, response, savedRequest);
    }

    private void responseAuthenticationInfo(Authentication authentication, HttpServletResponse response, SavedRequest savedRequest)
            throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (savedRequest != null) {
            userDetails.setRedirectUrl(savedRequest.getRedirectUrl());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().write(objectMapper.writeValueAsString(userDetails));
        } catch (IOException e) {
            LOGGER.error("Error when try to parse authentication info to json data");
            LOGGER.error(userDetails.toString(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
