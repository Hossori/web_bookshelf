package com.hsr.controller;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsr.constant.PathConst;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.service.BookshelfService;
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
    private final BookService bookService;
    private final MessageSource messageSource;

    @GetMapping("/detail")
    public String detail(
            Model model,
            @PageableDefault(size=10) Pageable pageable,
            @RequestParam int page,
            @RequestParam int userId) {

        User user = userService.getById(userId);
        if(user == null) {
            throw new IllegalArgumentException();
        }

        UserView userView = UserConverter.toView(user);

        model.addAttribute("user", user);
        model.addAttribute("userView", userView);

        // このuserのbookshelf一覧に飛ぶurl作る、これはhtmlで作れそう。
        return PathConst.USER_DETAIL.getValue();

    }

}
