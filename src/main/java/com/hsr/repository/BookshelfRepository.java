package com.hsr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsr.constant.JpaConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Integer> {

    /**
     * get page of all bookshelfs
     * @param pageable
     * @return page of bookshelf
     */
    @Query(JpaConst.BOOKSHELF_GETPAGES)
    public Page<Bookshelf> getPages(Pageable pageable);

    /**
     * get page of bookshelfs specified by user.
     * @param pageable
     * @param bookshelf
     * @return page of bookshelf
     */
    @Query(JpaConst.BOOKSHELF_GETPAGES_SPECIFIED_USER)
    public Page<Bookshelf> getPagesSpecifiedUser(Pageable pageable, @Param(JpaConst.PARAM_USER) User user);
}
