package com.hsr.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsr.domain.user.model.User;
import com.hsr.rest.Result;

@RestController
@RequestMapping("/rest/authChecker")
public class AuthChecker {

    public static boolean isLogin(HttpServletRequest request) {
        return request.getSession().getAttribute("loginUser") != null;
    }

    @GetMapping("/getLoginUserId")
    public Result getLoginUserId(@AuthenticationPrincipal User loginUser ) {
        Map<String, String> resultMap = new HashMap<>();
        if(loginUser != null) {
            resultMap.put("loginUserId", loginUser.getId().toString());
        }

        return new Result(HttpStatus.OK.value(), resultMap);
    }

}
