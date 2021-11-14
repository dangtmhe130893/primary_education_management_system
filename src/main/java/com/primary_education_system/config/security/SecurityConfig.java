package com.primary_education_system.config.security;

import com.primary_education_system.config.CustomAuthenticationEntryPoint;
import com.primary_education_system.filter.ChangePasswordFilter;
import com.primary_education_system.enum_type.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import java.util.TimeZone;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider customAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler restApiAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler restApiAuthenticationFailureHandler;


    @Autowired
    private ChangePasswordFilter changePasswordFilter;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/libs/**", "/custom/**", "/js/**", "/icon/**", "/images/**"
                , "libs/**", "custom/**", "js/**", "icon/**", "images/**", "favicon.ico/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/user/forgotPassword").permitAll()
                .antMatchers("/api/setPassword").permitAll()
                .antMatchers("/swagger-ui.html").hasAnyRole(Roles.SYSTEM_ADMIN.name());
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(restApiAuthenticationSuccessHandler)
                .failureHandler(restApiAuthenticationFailureHandler)
                .permitAll();
        http.exceptionHandling()
                .accessDeniedPage("/403");

        http.logout().logoutSuccessHandler(getLogoutSuccessHandler());

        http.authenticationProvider(customAuthenticationProvider);

        //only width request login
        http.exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());

        http.csrf().disable();

        http.headers().frameOptions().deny();

        http.addFilterAfter(changePasswordFilter, SecurityContextPersistenceFilter.class);
        http.addFilterAfter(changePasswordFilter, SecurityContextPersistenceFilter.class);
    }

    @Bean
    public LogoutSuccessHandler getLogoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    @Configuration
    static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
            return jacksonObjectMapperBuilder ->
                    jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        }
    }
}
