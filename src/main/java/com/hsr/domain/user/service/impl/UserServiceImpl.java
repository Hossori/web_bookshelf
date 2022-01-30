package com.hsr.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsr.domain.user.model.User;
import com.hsr.domain.user.service.UserService;
import com.hsr.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public void insertOne(User user) {
        userRepository.save(user);
    }

}
