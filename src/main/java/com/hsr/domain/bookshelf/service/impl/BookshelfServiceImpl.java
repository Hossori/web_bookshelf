package com.hsr.domain.bookshelf.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.constant.JpaConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;
import com.hsr.domain.user.model.User;
import com.hsr.repository.BookshelfRepository;

@Service
public class BookshelfServiceImpl implements BookshelfService {

    @Autowired
    private BookshelfRepository repository;

    @Override
    public Bookshelf getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public Bookshelf getByIdNotDeleted(Integer id) {
        return repository.getByIdNotDeleted(id);
    }

    @Override
    public Page<Bookshelf> getPages(Pageable pageable) {
        return repository.getPages(pageable);
    }

    @Override
    public Page<Bookshelf> getPagesSpecifiedUser(Pageable pageable, User user) {
        return repository.getPagesSpecifiedUser(pageable, user);
    }

    @Override
    @Transactional
    public HttpStatus create(Bookshelf bookshelf) {
        bookshelf.setCreatedAt(LocalDateTime.now());
        bookshelf.setUpdatedAt(LocalDateTime.now());
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        HttpStatus httpStatus =
                repository.save(bookshelf) != null
                    ? HttpStatus.OK
                    : HttpStatus.FORBIDDEN;
        return httpStatus;
    }

    @Override
    @Transactional
    public HttpStatus update(Bookshelf bookshelf, Bookshelf newBookshelf) {
        bookshelf.setName(newBookshelf.getName());
        bookshelf.setDescription(newBookshelf.getDescription());
        bookshelf.setUpdatedAt(LocalDateTime.now());
        HttpStatus httpStatus =
                repository.save(bookshelf) != null
                    ? HttpStatus.OK
                    : HttpStatus.FORBIDDEN;
        return httpStatus;
    }

    @Override
    @Transactional
    public void delete(Bookshelf bookshelf) {
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_TRUE);
        repository.save(bookshelf);
    }

}
