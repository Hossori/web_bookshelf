package com.hsr.domain.bookshelf.model.converter;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.hsr.domain.bookshelf.form.BookshelfCreateForm;
import com.hsr.domain.bookshelf.form.BookshelfEditForm;
import com.hsr.domain.bookshelf.model.Bookshelf;
import com.hsr.domain.bookshelf.model.BookshelfView;
import com.hsr.domain.converter.DomainConverter;

public class BookshelfConverter extends DomainConverter {

    /**
     * convert bookshelf to bookshelf view.
     * @param bookshelf
     * @return bookshelf view
     */
    public static BookshelfView toView(Bookshelf bookshelf, ZoneId zoneId) {

        BookshelfView bookshelfView =
                new BookshelfView(
                    String.valueOf(bookshelf.getId()),
                    bookshelf.getUser(),
                    bookshelf.getName(),
                    bookshelf.getDescription(),
                    formatDateTime(
                        toLocalDateTime(bookshelf.getCreatedEpochSecond(), zoneId)
                    ),
                    formatDateTime(
                        toLocalDateTime(bookshelf.getUpdatedEpochSecond(), zoneId)
                    ),
                    formatDate(
                        toLocalDate(bookshelf.getUpdatedEpochSecond(), zoneId)
                    )
                );

        return bookshelfView;

    }

    /**
     * convert bookshelf list to bookshelf view list.
     * @param bookshelf list
     * @return bookshelf view list
     */
    public static List<BookshelfView> toViewList(List<Bookshelf> bookshelf, ZoneId zoneId) {

        return bookshelf.stream()
                    .map(bs -> BookshelfConverter.toView(bs, zoneId))
                    .collect(Collectors.toList());

    }

    /**
     * convert bookshelf edit form to bookshelf.
     * @param bookshelf edit form
     * @return bookshelf
     */
    public static Bookshelf toModel(BookshelfEditForm bookshelfEditForm) {

        Bookshelf bookshelf =
                new Bookshelf(
                    bookshelfEditForm.getId(),
                    bookshelfEditForm.getUser(),
                    bookshelfEditForm.getName(),
                    bookshelfEditForm.getDescription(),
                    null,
                    null,
                    null
                );

        return bookshelf;

    }

    /**
     * convert bookshelf create form to bookshelf.
     * @param bookshelf create form
     * @return bookshelf
     */
    public static Bookshelf toModel(BookshelfCreateForm bookshelfCreateForm) {

        Bookshelf bookshelf =
                new Bookshelf(
                    null,
                    bookshelfCreateForm.getUser(),
                    bookshelfCreateForm.getName(),
                    bookshelfCreateForm.getDescription(),
                    null,
                    null,
                    null
                );

        return bookshelf;

    }

    /**
     * convert bookshelf to bookshelf edit form.
     * @param bookshelf
     * @return bookshelf edit form
     */
    public static BookshelfEditForm toEditForm(Bookshelf bookshelf) {

        BookshelfEditForm bookshelfEditForm =
                new BookshelfEditForm(
                    bookshelf.getId(),
                    bookshelf.getUser(),
                    bookshelf.getName(),
                    bookshelf.getDescription()
                );

        return bookshelfEditForm;

    }

    /**
     * convert bookshelf to bookshelf create form.
     * @param bookshelf
     * @return bookshelf create form
     */
    public static BookshelfCreateForm toCreateForm(Bookshelf bookshelf) {

        BookshelfCreateForm bookshelfCreateForm =
                new BookshelfCreateForm(
                    bookshelf.getUser(),
                    bookshelf.getName(),
                    bookshelf.getDescription()
                );

        return bookshelfCreateForm;

    }
}
