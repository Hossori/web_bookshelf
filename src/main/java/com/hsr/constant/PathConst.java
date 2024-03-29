package com.hsr.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PathConst {

    AUTH_LOGIN("auth/login"),
    AUTH_SIGNUP("auth/signup"),
    BOOKSHELF_INDEX("bookshelf"+BasePathEnum.INDEX.getValue()),
    BOOKSHELF_DETAIL("bookshelf"+BasePathEnum.DETAIL.getValue()),

    BOOK_INDEX("book"+BasePathEnum.INDEX.getValue()),
    BOOK_DETAIL("book"+BasePathEnum.DETAIL.getValue()),

    USER_DETAIL("user"+BasePathEnum.DETAIL.getValue()),

    ERROR("error");

    @Getter
    private final String value;

    @RequiredArgsConstructor
    private enum BasePathEnum {

        INDEX("/index"),
        DETAIL("/detail"),
        CREATE("/create"),
        UPDATE("/update"),
        DELETE("/delete");

        @Getter
        private final String value;

    }

}
