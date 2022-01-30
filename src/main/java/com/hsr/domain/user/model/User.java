package com.hsr.domain.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hsr.constant.JpaConst;

import lombok.Data;

@Data
@Entity
@Table(name=JpaConst.TABLE_USER)
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer gender;
    private LocalDate birthday;
    private String introduction;
    private LocalDateTime createdAt;

}
