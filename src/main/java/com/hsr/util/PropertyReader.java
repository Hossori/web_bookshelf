package com.hsr.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.constant.KeyConst;
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
            @RequestParam String key,
            Locale locale) {

        String prop = messageSource.getMessage(key, null, locale);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("value", prop);

        return new Result(0, resultMap);
    }

    @GetMapping("/get/keyConst")
    public Result getKeyConst(
            @RequestParam String keyName) {

        String key = KeyConst.valueOf(keyName).getKey();
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("key", key);

        return new Result(0, resultMap);
    }

    /**
     * get http status code.
     * @param enumerator name
     * @return status code
     */
    @GetMapping("/get/statusCode")
    public Integer getStatusCode(@RequestParam("key") String enumeratorName) {
        return HttpStatus.valueOf(enumeratorName).value();
    }

}
