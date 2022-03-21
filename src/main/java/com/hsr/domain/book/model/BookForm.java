package com.hsr.domain.book.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.hsr.domain.bookshelf.model.Bookshelf;

import lombok.Data;

@Data
public class BookForm {
    private Integer id;
    private Bookshelf bookshelf;
    @NotBlank
    private String name;
    private Integer state;
    private Integer evaluation;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleteFlag;
}
