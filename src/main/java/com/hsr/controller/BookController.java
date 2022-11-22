package com.hsr.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsr.constant.GlobalConst;
import com.hsr.constant.PathConst;
import com.hsr.domain.book.form.BookEditForm;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.model.BookView;
import com.hsr.domain.book.model.converter.BookConverter;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.service.UserService;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/index")
    public String idnex(
            Model model,
            @PageableDefault(size=10) Pageable pageable,
            @AuthenticationPrincipal User loginUser,
            @RequestParam int page,
            @RequestParam(defaultValue="-1") int userId) {

        Page<Book> bookPages;
        if(userId == GlobalConst.INVALID_ID) {
            bookPages = bookService.getPages(pageable);
        } else {
            User user = userService.getById(userId);
            if(user == null) {
                throw new IllegalArgumentException();
            }
            bookPages = bookService.getPagesSpecifiedUser(pageable, user);
        }

        List<Book> bookList = bookPages.getContent();
        List<BookView> bookViewList = BookConverter.toViewList(bookList);

        int pageCount =
                bookPages.getTotalPages() == 0 ? 1 : bookPages.getTotalPages();
        if(pageCount-1 < page) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("bookPages", bookPages);
        model.addAttribute("bookViews", bookViewList);

        return PathConst.BOOK_INDEX.getValue();

    }

    @GetMapping("/detail")
    public String detail(
            Model model,
            @RequestParam int id) {

        Book book = bookService.getByIdNotDeleted(id);
        if (book == null) {
            return PathConst.ERROR.getValue();
        }
        BookEditForm bookEditForm = BookConverter.toEditForm(book);
        BookView bookView = BookConverter.toView(book);
        List<String> states = List.of(messageSource.getMessage("book.state.array", null, Locale.getDefault()).split(", "));

        model.addAttribute("book", book);
        model.addAttribute("bookEditForm", bookEditForm);
        model.addAttribute("bookView", bookView);
        model.addAttribute("states", states);

        return PathConst.BOOK_DETAIL.getValue();
    }

}
