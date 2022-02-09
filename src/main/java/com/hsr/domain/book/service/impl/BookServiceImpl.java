package com.hsr.domain.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.service.BookService;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository repository;

    @Override
    public Book getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public Page<Book> getPages(Pageable pageable) {
        return repository.getPages(pageable);
    }

    @Override
    public Page<Book> getPagesInBookshelf(Pageable pageable, Bookshelf bookshelf) {
        return repository.getPagesInBookshelf(pageable, bookshelf);
    }

}
