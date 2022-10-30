package com.hsr.domain.bookshelf.model.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.model.BookshelfView;

public class BookshelfConverter {

    /**
     * convert bookshelf to bookshelf view.
     * @param bookshelf
     * @return bookshelf view
     */
    public static BookshelfView toView(Bookshelf bookshelf) {

        BookshelfView bookshelfView =
                new BookshelfView(
                    String.valueOf(bookshelf.getId()),
                    bookshelf.getUser(),
                    bookshelf.getName(),
                    bookshelf.getDescription(),
                    formatDateTime(bookshelf.getCreatedAt()),
                    formatDateTime(bookshelf.getUpdatedAt()),
                    formatDate(bookshelf.getUpdatedAt().toLocalDate())
                );

        return bookshelfView;

    }

    /**
     * convert bookshelf list to bookshelf view list.
     * @param bookshelf list
     * @return bookshelf view list
     */

    public static List<BookshelfView> toViewList(List<Bookshelf> bookshelf) {

        return bookshelf.stream()
                    .map(BookshelfConverter::toView)
                    .collect(Collectors.toList());

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
