package com.hsr.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserView {

    private String email;
    private String name;
    private String gender;
    private String birthday;
    private String introduction;
    private String createdAt;

}
