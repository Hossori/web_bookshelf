package com.hsr.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsr.constant.PathConst;
import com.hsr.domain.bookshelf.service.BookshelfService;

@Controller
@RequestMapping("/bookshelf")
public class BookshelfController {

    @Autowired
    private BookshelfService bookshelfService;

    @GetMapping
    public String indexBookshelf(Model model,
            @PageableDefault(page=0, size=10) Pageable pageable,
            Locale locale) {

        return PathConst.BOOKSHELF_INDEX.getValue();

    }

}
