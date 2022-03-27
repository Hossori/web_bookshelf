package com.hsr.domain.bookshelf.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.constant.JpaConst;
import com.hsr.constant.StatusCodeConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;
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
    public Page<Bookshelf> getPages(Pageable pageable) {
        return repository.getPages(pageable);
    }

    @Override
    @Transactional
    public void create(Bookshelf bookshelf) {
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        repository.save(bookshelf);
    }

    @Override
    @Transactional
    public Integer update(Bookshelf bookshelf, Bookshelf newBookshelf) {
        bookshelf.setName(newBookshelf.getName());
        bookshelf.setDescription(newBookshelf.getDescription());
        bookshelf.setUpdatedAt(LocalDateTime.now());
        Integer resultCode =
                repository.save(bookshelf) != null
                    ? StatusCodeConst.OK
                    : StatusCodeConst.FORBIDDEN;
        return resultCode;
    }

    @Override
    @Transactional
    public void delete(Bookshelf bookshelf) {
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_TRUE);
        repository.save(bookshelf);
    }

}
