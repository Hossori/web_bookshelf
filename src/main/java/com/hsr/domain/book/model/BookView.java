package com.hsr.domain.book.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsr.domain.bookshelf.model.Bookshelf;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BookView {

    private String id;
    private Bookshelf bookshelf;
    private String name;
    private String state;
    private String evaluation;
    private String memo;
    private String createdAt;
    private String createdDate;
    private String updatedAt;

}
