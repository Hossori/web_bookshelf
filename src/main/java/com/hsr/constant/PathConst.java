package com.hsr.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PathConst {

    BOOKSHELF_INDEX("bookshelf"+BasePathEnum.INDEX.getValue()),
    BOOKSHELF_SHOW("bookshelf"+BasePathEnum.SHOW.getValue()),

    BOOK_DETAIL("book"+BasePathEnum.DETAIL.getValue());

    @Getter
    private final String value;

    @RequiredArgsConstructor
    private enum BasePathEnum {

        INDEX("/index"),
        DETAIL("/detail"),
        SHOW("/show"),
        CREATE("/create"),
        UPDATE("/update"),
        DELETE("/delete");

        @Getter
        private final String value;

    }

}
