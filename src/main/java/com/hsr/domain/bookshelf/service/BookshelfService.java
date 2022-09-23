package com.hsr.domain.bookshelf.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Service
public interface BookshelfService {

    public Bookshelf getById(Integer id);

    public Page<Bookshelf> getPages(Pageable pageable);

    public Page<Bookshelf> getPagesSpecifiedUser(Pageable pageable, User user);

    public void create(Bookshelf bookshelf);

    public Integer update(Bookshelf bookshelf, Bookshelf newBookshelf);

    public void delete(Bookshelf bookshelf);

}
