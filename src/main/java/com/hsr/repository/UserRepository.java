package com.hsr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsr.constant.JpaConst;
import com.hsr.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(JpaConst.USER_GET_BY_EMAIL)
    public User getByEmail(String email);

}
