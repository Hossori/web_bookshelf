package com.hsr.rest;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.form.BookshelfCreateForm;
import com.hsr.domain.bookshelf.form.BookshelfEditForm;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.model.converter.BookshelfConverter;
import com.hsr.domain.bookshelf.service.BookshelfService;
import com.hsr.domain.user.model.User;
import com.hsr.validation.FormValidator;

@RestController
@RequestMapping("/rest/bookshelf")
public class BookshelfRestController {

    @Autowired
    private BookshelfService bookshelfService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MessageSource messageSource;

    /*@GetMapping("/getBookshelfPages")
    public Result getBookshelfPages(
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam("page") int page) {

        Page<Bookshelf> bookshelfPages = bookshelfService.getPages(pageable);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bookshelfPages", bookshelfPages);

        return new Result(0, resultMap);
    }*/

    @PutMapping("/create")
    public Result create(
            @ModelAttribute @Validated BookshelfCreateForm bookshelfCreateForm,
            BindingResult bindingResult,
            Locale locale) {

        HttpStatus httpStatus;

        Bookshelf bookshelf = BookshelfConverter.toModel(bookshelfCreateForm);

        Map<String, String> errors = FormValidator.validate(bindingResult, locale);
        if(errors != null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new Result(httpStatus.value(), errors);
        }

        httpStatus = bookshelfService.create(bookshelf);

        return new Result(httpStatus.value(), null);

    }

    @PutMapping("/update")
    public Result update(
            @AuthenticationPrincipal User loginUser,
            @ModelAttribute @Validated BookshelfEditForm bookshelfEditForm,
            BindingResult bindingResult,
            Locale locale) {

        Bookshelf bookshelf = bookshelfService.getById(bookshelfEditForm.getId());
        Bookshelf newBookshelf = BookshelfConverter.toModel(bookshelfEditForm);

        HttpStatus httpStatus;

        if (!loginUser.equals(bookshelf.getUser())) {
            httpStatus = HttpStatus.FORBIDDEN;
            return new Result(httpStatus.value(), null);
        }

        Map<String, String> errors = FormValidator.validate(bindingResult, locale);
        if (errors != null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new Result(httpStatus.value(), errors);
        }

        httpStatus = bookshelfService.update(bookshelf, newBookshelf);

        return new Result(httpStatus.value(), null);

    }

    @PutMapping("/delete")
    public Result delete(
            @RequestParam int bookshelfId,
            @AuthenticationPrincipal User loginUser) {
        Bookshelf bookshelf = bookshelfService.getById(bookshelfId);
        List<Book> bookListInBookshelf = bookService.getBookListInBookshelf(bookshelf);
        Integer resultCode;
        if(loginUser.equals(bookshelf.getUser())) {
            bookshelfService.delete(bookshelf);
            bookListInBookshelf.forEach(bookService::delete);
            resultCode = HttpStatus.OK.value();
        } else {
            resultCode = HttpStatus.FORBIDDEN.value();
        }
        return new Result(resultCode, null);
    }
}