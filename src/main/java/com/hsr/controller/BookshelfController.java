package com.hsr.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsr.constant.PathConst;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.model.BookView;
import com.hsr.domain.book.model.converter.BookConverter;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookshelf")
public class BookshelfController {

    private final BookshelfService bookshelfService;
    private final BookService bookService;

    @GetMapping("/index")
    public String index(
            Model model,
            @RequestParam("page") int page) {

        model.addAttribute(page);
        return PathConst.BOOKSHELF_INDEX.getValue();

    }

    @GetMapping("/show")
    public String show(
            Model model,
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam("id") int id,
            @RequestParam("page") int page) {

        Bookshelf bookshelf = bookshelfService.getById(id);
        Page<Book> bookPages = bookService.getPagesInBookshelf(pageable, bookshelf);
        List<Book> bookList = bookPages.getContent();
        List<BookView> bookViewList = BookConverter.toViewList(bookList);
        int pageCount = bookPages.getTotalPages();

        model.addAttribute(bookshelf);
        model.addAttribute("bookPages", bookPages);
        model.addAttribute("books", bookViewList);
        model.addAttribute("pageCount", pageCount);

        return PathConst.BOOKSHELF_SHOW.getValue();
    }

}