package com.hsr.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;

@Service
public interface BookService {

    public Book getById(Integer id);

    public Page<Book> getPages(Pageable pageable);

    public Page<Book> getPagesInBookshelf(Pageable pageable, Bookshelf bookshelf);

    public Book create(Book book);

    public Book update(Book book, Book newBook);

    public void delete(Book book);

}
