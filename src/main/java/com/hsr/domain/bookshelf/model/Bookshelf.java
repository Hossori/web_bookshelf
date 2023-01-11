package com.hsr.domain.bookshelf.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsr.constant.JpaConst;
import com.hsr.domain.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name=JpaConst.TABLE_BOOKSHELF)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Bookshelf {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;
    private String name;
    private String description;
    private Long createdEpochSecond;
    private Long updatedEpochSecond;
    private Integer deleteFlag;

}
