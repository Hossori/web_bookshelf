package com.hsr.domain.bookshelf.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hsr.domain.bookshelf.model.Bookshelf;

@Service
public interface BookshelfService {

	public Bookshelf getById(Integer id);
	
    public Page<Bookshelf> getPages(Pageable pageable);

    public void insertOne(Bookshelf bookshelf);

}
