package com.hsr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsr.constant.JpaConst;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.bookshelf.model.Bookshelf;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

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
	public Page<Book> getPagesInBookshelf(Pageable pageable, 
										@Param(JpaConst.PARAM_BOOKSHELF) Bookshelf bookshelf);
}
