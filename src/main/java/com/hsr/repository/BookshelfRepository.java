package com.hsr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsr.constant.JpaConst;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.user.model.User;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Integer> {

    @Query(JpaConst.BOOKSHELF_GETPAGES)
    public Page<Bookshelf> getPages(Pageable pageable);

    @Query(JpaConst.BOOKSHELF_GETPAGES_SPECIFIED_USER)
    public Page<Bookshelf> getPagesSpecifiedUser(Pageable pageable, User user);
}
