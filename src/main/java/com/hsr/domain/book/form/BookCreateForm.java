package com.hsr.domain.book.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hsr.domain.bookshelf.model.Bookshelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateForm {
    @NotNull
    private Bookshelf bookshelf;
    @NotBlank
    private String name;
    private Integer state;
    private Integer evaluation;
    private String memo;
}
