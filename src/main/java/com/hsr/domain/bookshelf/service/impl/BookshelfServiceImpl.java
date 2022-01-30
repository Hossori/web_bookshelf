package com.hsr.domain.bookshelf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.service.BookshelfService;
import com.hsr.repository.BookshelfRepository;

@Service
public class BookshelfServiceImpl implements BookshelfService {

    @Autowired
    private BookshelfRepository repository;

    @Override
    public Page<Bookshelf> getPages(Pageable pageable) {

        return repository.getPages(pageable);

    }

}
