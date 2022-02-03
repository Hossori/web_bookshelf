package com.hsr.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;

@Service
public interface BookService {

	public Page<Book> getPages(Pageable pageable);
	
	public Page<Book> getPagesInBookshelf(Pageable pageable, Bookshelf bookshelf);
	
}