package com.hsr.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;

@Service
public interface BookService {

	/**
	 * get book specified by id.
	 * @param id
	 * @return book
	 */
    public Book getById(Integer id);

    /**
     * get page of all books.
     * @param pageable
     * @return page of book
     */
    public Page<Book> getPages(Pageable pageable);

    /**
     * get page of books specified in bookshelf.
     * @param pageable
     * @param bookshelf
     * @return page of book
     */
    public Page<Book> getPagesInBookshelf(Pageable pageable, Bookshelf bookshelf);

    /**
     * register book.
     * @param book
     * @return http status
     */
    public HttpStatus create(Book book);

    /**
     * update book.
     * @param book
     * @param newBook
     * @return http status
     */
    public HttpStatus update(Book book, Book newBook);

    /**
     * delete book.
     * @param book
     */
    public void delete(Book book);

}
