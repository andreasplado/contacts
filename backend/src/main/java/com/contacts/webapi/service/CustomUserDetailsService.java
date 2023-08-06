package com.contacts.webapi.service;

import com.contacts.webapi.dao.entity.UserEntity;
import com.contacts.webapi.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Returning dummy user, use your own logic for example load from
        // database
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(("ROLE_USER")));
        List<UserEntity> users = userRepository.findByUsername(username);
        if(!users.isEmpty()) {
            User user = new User(users.get(0).getUsername(), users.get(0).getPassword(), authorities);
            System.out.println("user : " + user.getUsername());
            return user;
        }else{
            return null;
        }
    }
}