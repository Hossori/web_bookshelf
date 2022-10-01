package com.hsr.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.domain.user.model.User;

@Service
public interface UserService {

	/**
	 * get user specified by id.
	 * @param user id
	 * @return user
	 */
    public User getById(Integer id);
    
    /**
     * register user.
     * @param user
     * @return http status
     */
    public HttpStatus signup(User user);

}
