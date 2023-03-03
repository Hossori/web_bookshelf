package com.hsr.constant;

import lombok.Getter;

public enum KeyConst {
    CLIENT_ZONE_ID_KEY("clientZoneId"),
    SAVED_REQUEST_KEY("SPRING_SECURITY_SAVED_REQUEST");

    @Getter
    private String key;

    KeyConst(String key) {
        this.key = key;
    }
}
