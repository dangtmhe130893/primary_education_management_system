package com.primary_education_system.config.security;

import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.service.PupilAccountService;
import com.primary_education_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        LOGGER.info("Validate web user with email:  {}", email);
        UserEntity userEntity = userService.findByEmail(email);
        if (userEntity != null) {
            if (!passwordEncoder.matches(password, userEntity.getPassword())) {
                throw new BadCredentialsException("Username or password is incorrect!");
            }
            Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (RoleEntity role : userEntity.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            UserDetails userDetails = new CustomUserDetails(email, password, userEntity.getId(), authorities);
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        }
        // login pupil
        PupilAccountEntity pupil = pupilAccountService.findByEmail(email);
        if (pupil == null || !passwordEncoder.matches(password, pupil.getPassword())) {
            throw new BadCredentialsException("Username or password is incorrect!");
        }
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("PUPIL_PARENT"));
        UserDetails userDetails = new CustomUserDetails(email, password, pupil.getId(), authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);


    }

    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
