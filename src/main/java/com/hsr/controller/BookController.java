package com.hsr.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/detail")
    public String detail(
            Model model,
            @RequestParam("id") int id) {

        Book book = bookService.getById(id);
        BookView bookView = BookConverter.toView(book);
        List<String> states = List.of(messageSource.getMessage("book.state.array", null, Locale.getDefault()).split(", "));

        model.addAttribute("book", book);
        model.addAttribute("bookView", bookView);
        model.addAttribute("states", states);

        return PathConst.BOOK_DETAIL.getValue();
    }

}
