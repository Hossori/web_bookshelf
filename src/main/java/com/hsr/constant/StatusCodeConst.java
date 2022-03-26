package com.hsr.constant;

import com.hsr.util.PropertyReader;

public interface StatusCodeConst {

    public static final Integer OK = PropertyReader.getStatusCode("statusCode.ok");
    public static final Integer BAD_REQUEST = PropertyReader.getStatusCode("statusCode.badRequest");
    public static final Integer FORBIDDEN = PropertyReader.getStatusCode("statusCode.forbidden");

}
