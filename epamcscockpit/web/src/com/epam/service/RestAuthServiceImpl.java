package com.epam.service;

import com.epam.dto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public final class RestAuthServiceImpl implements RestAuthService {

    @Autowired
    private UserDetailsService coreUserDetailsService;

    @Override
    public final Credentials getCredentials() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = coreUserDetailsService.loadUserByUsername(name);
        return new Credentials(userDetails.getUsername(), userDetails.getPassword().toCharArray());
    }
}
