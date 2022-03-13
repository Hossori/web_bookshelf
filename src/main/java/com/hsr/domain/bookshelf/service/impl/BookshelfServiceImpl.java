package com.hsr.domain.bookshelf.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.constant.JpaConst;
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
    public void create(Bookshelf bookshelf) {
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_FALSE);
        repository.save(bookshelf);
    }

    @Override
    public Bookshelf update(Bookshelf bookshelf, Bookshelf newBookshelf) {
        bookshelf.setName(newBookshelf.getName());
        bookshelf.setDescription(newBookshelf.getDescription());
        bookshelf.setUpdatedAt(LocalDateTime.now());
        return repository.save(bookshelf);
    }

    @Override
    public void delete(Bookshelf bookshelf) {
        bookshelf.setDeleteFlag(JpaConst.DELETE_FLAG_TRUE);
        repository.save(bookshelf);
    }

}
