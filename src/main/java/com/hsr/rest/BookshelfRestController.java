package com.hsr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.constant.StatusCodeConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;

@RestController
@RequestMapping("/rest/bookshelf")
public class BookshelfRestController {

    @Autowired
    private BookshelfService bookshelfService;
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


    @PutMapping("/update")
    public Result update(
            @ModelAttribute Bookshelf newBookshelf) {

        Bookshelf bookshelf = bookshelfService.getById(newBookshelf.getId());
        int resultCode = bookshelfService.update(bookshelf, newBookshelf) != null
                        ? StatusCodeConst.OK
                        : StatusCodeConst.FORBIDDEN;
        return new Result(resultCode, null);

    }

    @PutMapping("/delete")
    public Result delete(
            @RequestParam("bookshelfId") int bookshelfId) {
        Bookshelf bookshelf = bookshelfService.getById(bookshelfId);
        bookshelfService.delete(bookshelf);
        int resultCode = StatusCodeConst.OK;
        return new Result(resultCode, null);
    }
}