package com.hsr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsr.constant.JpaConst;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * get book by id not deleted.
     * @param id
     * @return book
     */
    @Query(JpaConst.BOOK_GET_BY_ID_NOT_DELETED)
    public Book getByIdNotDeleted(Integer id);

    /**
     * get book list specified in bookshelf.
     * @param bookshelf
     * @return book list
     */
    @Query(JpaConst.BOOK_GET_BOOK_LIST_IN_BOOKSHELF)
    public List<Book> getBookListInBookshelf(Bookshelf bookshelf);

    /**
     * get page of all books.
     * @param pageable
     * @return page of book
     */
    @Query(JpaConst.BOOK_GETPAGES)
    public Page<Book> getPages(Pageable pageable);

    /**
     * get page of books specified by bookshelf.
     * @param pageable
     * @param bookshelf
     * @return page of book
     */
    @Query(JpaConst.BOOK_GETPAGES_IN_BOOKSHELF)
    public Page<Book> getPagesInBookshelf(
            Pageable pageable,
            @Param(JpaConst.PARAM_BOOKSHELF) Bookshelf bookshelf
    );

    /**
     * get page of books specified by user.
     * @param pageable
     * @param book
     * @return page of book
     */
    @Query(JpaConst.BOOK_GETPAGES_SPECIFIED_USER)
    public Page<Book> getPagesSpecifiedUser(
            Pageable pageable,
            @Param(JpaConst.PARAM_USER) User user
    );
}
