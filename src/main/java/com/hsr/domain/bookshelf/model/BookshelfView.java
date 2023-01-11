package com.hsr.domain.bookshelf.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsr.domain.user.model.User;

import lombok.AllArgsConstructor;
//import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BookshelfView {

    private String id;
    private User user;
    private String name;
    private String description;
    private String createdDateTime;
    private String updatedDateTime;
    private String updatedDate;

}
