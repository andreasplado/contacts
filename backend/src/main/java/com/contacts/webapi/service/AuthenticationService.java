package com.contacts.webapi.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationService {
    public static Authentication getAuthentication(HttpServletRequest request, UserDetailsService userDetailsService) {

        HttpServletRequest username = request;
        UserDetails user = userDetailsService.loadUserByUsername("tet");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());

        //Use following to indicate that authentication failed, if user not found or role doesn't match
        boolean hasAuthenticationFailed = false;

        if(hasAuthenticationFailed) {
            throw new AuthenticationException("lol"){};
        }

        return authentication;
    }
}