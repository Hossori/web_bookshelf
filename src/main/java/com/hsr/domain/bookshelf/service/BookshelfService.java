package com.hsr.domain.bookshelf.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Service
public interface BookshelfService {

    /**
     * get bookshelf specified by id.
     * @param bookshelf id
     * @return bookshelf
     */
    public Bookshelf getById(Integer id);

    /**
     * get not deleted bookshelf specified by id.
     * @param bookshelf id
     * @return bookshelf
     */
    public Bookshelf getByIdNotDeleted(Integer id);

    /**
     * get page of all bookshelfs.
     * @param pageable
     * @return page of bookshelf
     */
    public Page<Bookshelf> getPages(Pageable pageable);

    /**
     * get page of bookshelfs specified by user.
     * @param pageable
     * @param user
     * @return page of bookshelf
     */
    public Page<Bookshelf> getPagesSpecifiedUser(Pageable pageable, User user);

    /**
     * register bookshelf
     * @param bookshelf
     * @return http status
     */
    public HttpStatus create(Bookshelf bookshelf);

    /**
     * update bookshelf.
     * @param bookshelf
     * @param newBookshelf
     * @return http status
     */
    public HttpStatus update(Bookshelf bookshelf, Bookshelf newBookshelf);

    /**
     * delete bookshelf.
     * @param bookshelf
     */
    public void delete(Bookshelf bookshelf);

}
