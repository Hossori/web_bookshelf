package com.hsr.domain.book.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.constant.JpaConst;
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

    @Override
    @Transactional
    public Integer create(Book book) {

        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        Integer resultCode =
                repository.save(book) != null
                    ? HttpStatus.OK.value()
                    : HttpStatus.FORBIDDEN.value();
        return resultCode;

    }

    @Override
    @Transactional
    public Integer update(Book book, Book newBook) {

        book.setName(newBook.getName());
        book.setState(newBook.getState());
        book.setEvaluation(newBook.getEvaluation());
        book.setMemo(newBook.getMemo());
        book.setUpdatedAt(LocalDateTime.now());
        Integer resultCode =
                repository.save(book)!= null
                    ? HttpStatus.OK.value()
                    : HttpStatus.FORBIDDEN.value();
        return resultCode;

    }

    @Override
    @Transactional
    public void delete(Book book) {
        book.setDeleteFlag(JpaConst.DELETE_FLAG_TRUE);
        repository.save(book);
    }

}
