package com.hsr.domain.book.model.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.hsr.domain.book.form.BookEditForm;
import com.hsr.domain.book.model.Book;
import com.hsr.domain.book.model.BookView;

@Component
public class BookConverter {

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

    private static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.format(formatter);
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return dateTime.format(formatter);
    }

}
