package com.hsr.constant;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import lombok.Getter;

public enum StatusCodeConst {

    OK("statusCode.ok"),
    BAD_REQUEST("statusCode.badRequest"),
    FORBIDDEN("statusCode.forbidden");

    @Autowired
    private MessageSource messageSource;

    @Getter
    private Integer value;

    private StatusCodeConst(String key) {
        this.value = Integer.parseInt(messageSource.getMessage(key, null, Locale.getDefault()));
    }
}
