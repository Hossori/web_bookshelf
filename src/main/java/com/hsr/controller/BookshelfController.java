package com.hsr.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
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
import com.hsr.domain.bookshelf.model.BookshelfView;
import com.hsr.domain.bookshelf.model.converter.BookshelfConverter;
import com.hsr.domain.bookshelf.service.BookshelfService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookshelf")
public class BookshelfController {

    private final BookshelfService bookshelfService;
    private final BookService bookService;
    private final MessageSource messageSource;

    @GetMapping("/index")
    public String index(
            Model model,
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam("page") int page) {

        Page<Bookshelf> bookshelfPages = bookshelfService.getPages(pageable);
        List<Bookshelf> bookshelfList = bookshelfPages.getContent();
        List<BookshelfView> bookshelfViewList = BookshelfConverter.toViewList(bookshelfList);
        int pageCount =
                bookshelfPages.getTotalPages() == 0 ? 1 : bookshelfPages.getTotalPages();

        model.addAttribute("bookshelfPages", bookshelfPages);
        model.addAttribute("bookshelfs", bookshelfViewList);
        model.addAttribute("pageCount", pageCount);

        return PathConst.BOOKSHELF_INDEX.getValue();

    }

    @GetMapping("/detail")
    public String detail(
            Model model,
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam("id") int id,
            @RequestParam("page") int page) {

        Bookshelf bookshelf = bookshelfService.getById(id);
        Book book = new Book(); // for register book
        book.setBookshelf(bookshelf);
        List<String> states = List.of(messageSource.getMessage("book.state.array", null, Locale.getDefault()).split(", "));
        Page<Book> bookPages = bookService.getPagesInBookshelf(pageable, bookshelf);
        List<Book> bookList = bookPages.getContent();
        List<BookView> bookViewList = BookConverter.toViewList(bookList);
        int pageCount =
                bookPages.getTotalPages() == 0 ? 1 : bookPages.getTotalPages();

        model.addAttribute(bookshelf);
        model.addAttribute(book);
        model.addAttribute("states", states);
        model.addAttribute("bookPages", bookPages);
        model.addAttribute("books", bookViewList);
        model.addAttribute("pageCount", pageCount);

        return PathConst.BOOKSHELF_DETAIL.getValue();
    }

}