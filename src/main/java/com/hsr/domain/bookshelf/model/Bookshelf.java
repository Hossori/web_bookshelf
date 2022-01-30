package com.hsr.domain.bookshelf.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hsr.constant.JpaConst;
import com.hsr.domain.user.model.User;

import lombok.Data;

@Data
@Entity
@Table(name=JpaConst.TABLE_BOOKSHELF)
public class Bookshelf {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
