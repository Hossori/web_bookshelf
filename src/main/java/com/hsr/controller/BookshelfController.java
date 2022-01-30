package com.hsr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsr.constant.PathConst;

@Controller
@RequestMapping("/bookshelf")
public class BookshelfController {

    @GetMapping
    public String indexBookshelf() {
        return PathConst.BOOKSHELF_INDEX.getValue();
    }

}
