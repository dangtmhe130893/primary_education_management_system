package com.primary_education_system.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class GlobalExceptionHandlerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerFilter.class);

    @Value("${isOnSendMailError}")
    public boolean isOnSendMailError;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (isOnSendMailError) {
            try {
                chain.doFilter(request, response);
            } catch (Exception ex) {
                if (ex instanceof ClientAbortException) {
                    LOGGER.error("Client abort request!!");
                } else if (ex.getMessage().contains("Unexpected EOF read on the socket") || ex.getMessage().contains("Processing of multipart/form-data request failed")) {
                    LOGGER.error("Request upload file had been closed by client !!!");
                } else {
                    LOGGER.error("Error is: ", ex);
                    throw ex;
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) {

    }


}
