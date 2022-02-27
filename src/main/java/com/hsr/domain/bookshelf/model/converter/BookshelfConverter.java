package com.hsr.domain.bookshelf.model.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.model.BookshelfView;

public class BookshelfConverter {

    public static BookshelfView toView(Bookshelf bookshelf) {

        BookshelfView bookshelfView =
                new BookshelfView(
                    String.valueOf(bookshelf.getId()),
                    bookshelf.getUser(),
                    bookshelf.getName(),
                    bookshelf.getDescription(),
                    formatDateTime(bookshelf.getCreatedAt()),
                    formatDateTime(bookshelf.getUpdatedAt())
                );

        return bookshelfView;

    }

    public static List<BookshelfView> toViewList(List<Bookshelf> bookshelf) {

        return bookshelf.stream()
                    .map(BookshelfConverter::toView)
                    .collect(Collectors.toList());

    }

    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return dateTime.format(formatter);
    }

}
