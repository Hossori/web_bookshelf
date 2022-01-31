package com.hsr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsr.constant.PathConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;

@Controller
@RequestMapping("/bookshelf")
public class BookshelfController {

	@Autowired
	private BookshelfService bookshelfService;
	
    @GetMapping("/index")
    public String index() {
        return PathConst.BOOKSHELF_INDEX.getValue();
    }

    @GetMapping("/show")
    public String show(
    		Model model,
    		@RequestParam("id") int id) {
    	
    	Bookshelf bookshelf = bookshelfService.getById(id);
    	model.addAttribute(bookshelf);
    	
    	return PathConst.BOOKSHELF_SHOW.getValue();
    }
    
}