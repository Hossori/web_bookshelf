package com.hsr.domain.bookshelf.form;

import javax.validation.constraints.NotBlank;

import com.hsr.domain.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookshelfEditForm {
    private Integer id;
    private User user;
    @NotBlank
    private String name;
    private String description;
}
