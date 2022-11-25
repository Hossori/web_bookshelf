package com.hsr.domain.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Service
public interface BookService {

    /**
     * get book specified by id.
     * @param id
     * @return book
     */
    public Book getById(Integer id);

    /**
     * get not deleted book specified by id.
     * @param id
     * @return book
     */
    public Book getByIdNotDeleted(Integer id);

    /**
     * get book list specified in bookshelf.
     * @param bookshelf
     * @return book list
     */
    public List<Book> getBookListInBookshelf(Bookshelf bookshelf);

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
     * get page of books specified by user.
     * @param pageable
     * @param user
     * @return page of book
     */
    public Page<Book> getPagesSpecifiedUser(Pageable pageable, User user);

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
