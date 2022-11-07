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
import com.hsr.domain.bookshelf.form.BookshelfEditForm;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.model.BookshelfView;
import com.hsr.domain.bookshelf.model.converter.BookshelfConverter;
import com.hsr.domain.bookshelf.service.BookshelfService;
import com.hsr.domain.user.form.UserEditForm;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.UserView;
import com.hsr.domain.user.model.converter.UserConverter;
import com.hsr.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BookshelfService bookshelfService;
    private final MessageSource messageSource;

    @GetMapping("/detail")
    public String detail(
            Model model,
            Locale locale,
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam int page,
            @RequestParam int userId) {

        User user = userService.getById(userId);
        if(user == null) {
            throw new IllegalArgumentException();
        }

        UserEditForm userEditForm = UserConverter.toEditForm(user);
        UserView userView = UserConverter.toView(user);

        String[] genders = messageSource.getMessage("user.gender.array", null, locale).split(", ");

        Page<Bookshelf> bookshelfPages = bookshelfService.getPagesSpecifiedUser(pageable, user);
        List<BookshelfView> bookshelfViews = BookshelfConverter.toViewList(bookshelfPages.getContent());

        BookshelfEditForm bookshelfEditForm = new BookshelfEditForm(); // for register bookshelf
        bookshelfEditForm.setUser(user);

        model.addAttribute("user", user);
        model.addAttribute("userEditForm", userEditForm);
        model.addAttribute("userView", userView);
        model.addAttribute("genders", genders);
        model.addAttribute("bookshelfViews", bookshelfViews);
        model.addAttribute("bookshelfPages", bookshelfPages);
        model.addAttribute("bookshelfEditForm", bookshelfEditForm);

        return PathConst.USER_DETAIL.getValue();

    }

}
