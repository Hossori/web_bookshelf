package com.hsr.domain.book.service.impl;

import java.time.Instant;
import java.util.List;

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
import com.hsr.domain.user.model.User;
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
    public Book getByIdNotDeleted(Integer id) {
        return repository.getByIdNotDeleted(id);
    }

    @Override
    public List<Book> getBookListInBookshelf(Bookshelf bookshelf) {
        return repository.getBookListInBookshelf(bookshelf);
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
    public Page<Book> getPagesSpecifiedUser(Pageable pageable, User user) {
        return repository.getPagesSpecifiedUser(pageable, user);
    }

    @Override
    @Transactional
    public HttpStatus create(Book book) {

        book.setCreatedEpochSecond(Instant.now().getEpochSecond());
        book.setUpdatedEpochSecond(Instant.now().getEpochSecond());
        book.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        HttpStatus httpStatus =
                repository.save(book) != null
                    ? HttpStatus.OK
                    : HttpStatus.FORBIDDEN;
        return httpStatus;

    }

    @Override
    @Transactional
    public HttpStatus update(Book book, Book newBook) {

        book.setName(newBook.getName());
        book.setState(newBook.getState());
        book.setEvaluation(newBook.getEvaluation());
        book.setMemo(newBook.getMemo());
        book.setUpdatedEpochSecond(Instant.now().getEpochSecond());
        HttpStatus httpStatus =
                repository.save(book) != null
                    ? HttpStatus.OK
                    : HttpStatus.FORBIDDEN;
        return httpStatus;

    }

    @Override
    @Transactional
    public void delete(Book book) {
        book.setDeleteFlag(JpaConst.DELETE_FLAG_TRUE);
        repository.save(book);
    }

}
