package com.hsr.rest;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.constant.StatusCodeConst;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.model.BookForm;
import com.hsr.domain.book.model.validator.BookValidator;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.service.BookshelfService;

@RestController
@RequestMapping("/rest/book")
public class BookRestController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookshelfService bookshelfService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageSource messageSource;

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
    /*
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
    */

    @PutMapping("/create")
    public Result create(
            @ModelAttribute @Validated BookForm bookForm,
            BindingResult bindingResult,
            Locale locale) {

        int resultCode;

        Map<String, String> errors = BookValidator.validate(bindingResult, locale);
        if(errors != null) {
            resultCode = StatusCodeConst.BAD_REQUEST;
            return new Result(resultCode, errors);
        }

        Book book = modelMapper.map(bookForm, Book.class);
        resultCode = bookService.create(book);

        return new Result(resultCode, null);

    }

    @PutMapping("/update")
    public Result update(
            @ModelAttribute @Validated BookForm bookForm,
            BindingResult bindingResult,
            Locale locale) {

        int resultCode;

        Map<String, String> errors = BookValidator.validate(bindingResult, locale);
        if(errors != null) {
            resultCode = StatusCodeConst.BAD_REQUEST;
            return new Result(resultCode, errors);
        }

        Book book = bookService.getById(bookForm.getId());
        Book newBook = modelMapper.map(bookForm, Book.class);
        resultCode = bookService.update(book, newBook);

        return new Result(resultCode, null);

    }

    @PutMapping("/delete")
    public Result delete(
            @RequestParam("bookId") int bookId) {

        Book book = bookService.getById(bookId);
        bookService.delete(book);
        int resultCode = StatusCodeConst.OK;

        return new Result(resultCode, null);

    }

}
