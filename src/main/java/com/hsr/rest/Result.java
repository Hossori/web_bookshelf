package com.hsr.rest;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Result {

    private final int code;
    private final Map<String, String> data;

}
