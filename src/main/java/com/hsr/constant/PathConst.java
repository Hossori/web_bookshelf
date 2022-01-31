package com.hsr.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PathConst {

    BOOKSHELF_INDEX("bookshelf"+BasePathEnum.INDEX.getValue());
	
    @Getter
    private final String value;

    @RequiredArgsConstructor
    private enum BasePathEnum {

        INDEX("/index"),
        SHOW("/show"),
        CREATE("/create"),
        UPDATE("/update"),
        DELETE("/delete");

        @Getter
        private final String value;

    }

}
