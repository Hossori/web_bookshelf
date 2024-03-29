package com.hsr.domain.book.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsr.constant.JpaConst;
import com.hsr.domain.bookshelf.model.Bookshelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name=JpaConst.TABLE_BOOK)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Bookshelf bookshelf;
    private String name;
    private Integer state;
    private Integer evaluation;
    private String memo;
    private Long createdEpochSecond;
    private Long updatedEpochSecond;
    private Integer deleteFlag;

}
