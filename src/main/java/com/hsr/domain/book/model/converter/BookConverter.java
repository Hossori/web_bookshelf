package com.hsr.domain.book.model.converter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.hsr.domain.book.form.BookCreateForm;
import com.hsr.domain.book.form.BookEditForm;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.model.BookView;
import com.hsr.domain.converter.DomainConverter;

@Component
public class BookConverter extends DomainConverter {

    private static MessageSource messageSource;
    @Autowired
    public void setMessage(MessageSource messageSource) {
        BookConverter.messageSource = messageSource;
    }

    /**
     * convert book to book view.
     * @param book
     * @return book view
     */
    public static BookView toView(Book book) {

        BookView bookView =
                new BookView(
                    String.valueOf(book.getId()),
                    book.getBookshelf(),
                    book.getName(),
                    formatState(book.getState()),
                    formatEvaluation(book.getEvaluation()),
                    book.getMemo(),
                    formatDateTime(book.getCreatedAt()),
                    formatDate(book.getCreatedAt().toLocalDate()),
                    formatDateTime(book.getUpdatedAt())
                );

        return bookView;

    }

    /**
     * convert book list to book view list.
     * @param book list
     * @return book view list
     */
    public static List<BookView> toViewList(List<Book> bookList) {

        return bookList.stream()
                    .map(BookConverter::toView)
                    .collect(Collectors.toList());

    }

    /**
     * convert book create form to book.
     * @param book create form
     * @return book
     */
    public static Book toModel(BookCreateForm bookCreateForm) {

        Book book =
                new Book(
                    null,
                    bookCreateForm.getBookshelf(),
                    bookCreateForm.getName(),
                    bookCreateForm.getState(),
                    bookCreateForm.getEvaluation(),
                    bookCreateForm.getMemo(),
                    null,
                    null,
                    null
                );

        return book;

    }

    /**
     * convert book edit form to book.
     * @param book edit form
     * @return book
     */
    public static Book toModel(BookEditForm bookEditForm) {

        Book book =
                new Book(
                    bookEditForm.getId(),
                    bookEditForm.getBookshelf(),
                    bookEditForm.getName(),
                    bookEditForm.getState(),
                    bookEditForm.getEvaluation(),
                    bookEditForm.getMemo(),
                    null,
                    null,
                    null
                );

        return book;

    }

    /**
     * convert book to book create form.
     * @param book
     * @return book create form
     */
    public static BookCreateForm toCreateForm(Book book) {

        BookCreateForm bookCreateForm =
                new BookCreateForm(
                    book.getBookshelf(),
                    book.getName(),
                    book.getState(),
                    book.getEvaluation(),
                    book.getMemo()
                );

        return bookCreateForm;

    }

    /**
     * convert book to book edit form.
     * @param book
     * @return book edit form
     */
    public static BookEditForm toEditForm(Book book) {

        BookEditForm bookEditForm =
                new BookEditForm(
                    book.getId(),
                    book.getBookshelf(),
                    book.getName(),
                    book.getState(),
                    book.getEvaluation(),
                    book.getMemo()
                );

        return bookEditForm;

    }

    private static String formatState(Integer state) {
        String stateString ="";
        String[] states = messageSource.getMessage("book.state.array", null, Locale.getDefault()).split(", ");
        if(state == null) {
            stateString = messageSource.getMessage("book.state.unset", null, Locale.getDefault());
        } else {
            stateString = states[state];
        }
        return stateString;
    }

    private static String formatEvaluation(Integer evaluation) {
        String evaluationString = "";
        if(evaluation == null) {
            evaluationString = messageSource.getMessage("book.evaluation.unset", null, Locale.getDefault());
        } else {
            for(int i = 0; i < 5; i++) {
                if(i < evaluation) {
                    evaluationString += "★";
                } else {
                    evaluationString += "☆";
                }
            }
        }
        return evaluationString;
    }
}
