package com.hsr.domain.user.service;

import org.springframework.stereotype.Service;

import com.hsr.domain.user.model.User;

@Service
public interface UserService {

    public User getById(Integer id);
    public void signup(User user);

}
