package com.hsr.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.rest.Result;

@RestController
@RequestMapping("/rest/property")
public class PropertyReader {

    private static MessageSource messageSource;

    @Autowired
    public PropertyReader(MessageSource messageSource) {
        PropertyReader.messageSource = messageSource;
    }

    @GetMapping("/get")
    public Result getProperty(
            @RequestParam("key") String key,
            Locale locale) {

        String prop = messageSource.getMessage(key, null, locale);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("value", prop);

        return new Result(0, resultMap);
    }

    public static Integer getStatusCode(String key) {
        return Integer.parseInt(messageSource.getMessage(key, null, Locale.getDefault()));
    }

}
