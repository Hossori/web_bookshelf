package com.hsr.domain.user.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hsr.config.WebSecurityConfig;
import com.hsr.constant.JpaConst;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.service.UserService;
import com.hsr.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder encoder = new WebSecurityConfig().passwordEncoder();

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public HttpStatus signup(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRePassword(password); // for annotation of SameString.
        user.setCreatedAt(LocalDateTime.now());
        user.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        HttpStatus httpStatus =
                userRepository.save(user) != null
                    ? HttpStatus.OK
                    : HttpStatus.FORBIDDEN;
        return httpStatus;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        if(user == null) { throw new UsernameNotFoundException("user not found."); }
        return user;
    }

}
