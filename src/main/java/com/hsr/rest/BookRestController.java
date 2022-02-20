package com.hsr.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;

@RestController
@RequestMapping("/rest/book")
public class BookRestController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookshelfService bookshelfService;

    /*
    @GetMapping("/getBookPages")
    public Result getBookPages(
            @PageableDefault(page=0, size=10) Pageable pageable,
            @RequestParam("page") int page) {

        Page<Book> bookPages = bookService.getPages(pageable);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bookPages", bookPages);

        return new Result(0, resultMap);

    }
    */

    @GetMapping("/getBookPagesInBookshelf")
    public Result getBookPagesInBookshelf(
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam("page") int page,
            @RequestParam("bookshelfId") int bookshelfId) {

        Bookshelf bookshelf = bookshelfService.getById(bookshelfId);
        Page<Book> bookPages = bookService.getPagesInBookshelf(pageable, bookshelf);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bookPages", bookPages);

        return new Result(0, resultMap);
    }

    @GetMapping("/getBookDetail")
    public Result getBookDetail(
            @RequestParam("bookId") int bookId) {

        Book book = bookService.getById(bookId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("book", book);

        return new Result(0, resultMap);
    }

}
