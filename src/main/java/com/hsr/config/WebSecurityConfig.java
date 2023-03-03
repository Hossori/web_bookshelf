package com.hsr.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsr.constant.KeyConst;
import com.hsr.domain.user.form.UserLoginForm;
import com.hsr.rest.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
                               implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Validator validator;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(
            "/css/**",
            "/image/**",
            "/js/**",
            "/lib/**",
            "/error",
            "/signup",
            "/rest/auth/signup",
            "/rest/property/get",
            "/rest/property/get/keyConst",
            "/rest/property/get/statusCode",
            "/rest/session/put/zoneId"
        );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login").permitAll()
                    .usernameParameter("email")
                    .passwordParameter("password")
                .successHandler(this)
                .failureHandler(this)
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .addLogoutHandler((request, response, authentication) -> {
                    request.getSession().removeAttribute("loginUser");
                })
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authMgBuilder) throws Exception {
        authMgBuilder
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    // implementation of AuthenticationSuccessHandler --------
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        if(response.isCommitted()) {
            log.info("Response has already been committed.");
            return;
        }

        Map<String, String> loginSuccessParameters = getLoginSuccessParameters(request);
        Result restResult = new Result(HttpStatus.OK.value(), loginSuccessParameters);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(restResult));
        clearAuthenticationAttributes(request);

        request.getSession().setAttribute("loginUser", authentication.getPrincipal());

    }

    private Map<String, String> getLoginSuccessParameters(HttpServletRequest request) {
        Map<String, String> loginSuccessParameters = new HashMap<>();
        loginSuccessParameters.put("redirectUrl", getSavedRedirectUrl(request));

        return loginSuccessParameters;
    }

    private String getSavedRedirectUrl(HttpServletRequest request) {
        Object savedRequestObject = request.getSession().getAttribute(KeyConst.SAVED_REQUEST_KEY.getKey());
        if (savedRequestObject == null) {
            return null;
        }
        SimpleSavedRequest savedRequest = new SimpleSavedRequest((SavedRequest) savedRequestObject);

        return savedRequest.getRedirectUrl();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    // -------------------------------------------------------

    // implementation of AuthenticationFailureHandler --------
    @SuppressWarnings("deprecation")
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        Map<String, String> errors = validateLoginForm(
            request.getParameter("email"),
            request.getParameter("password"),
            request.getLocale()
        );
        Result restResult = new Result(HttpStatus.BAD_REQUEST.value(), errors);

        // it is needed using this deprecated member. otherwise Japanese characters become like ???.
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(restResult));

    }

    private Map<String, String> validateLoginForm(String email, String password, Locale locale) {
        Map<String, String> errors = new HashMap<>();
        UserLoginForm userLoginForm = new UserLoginForm(email, password);
        Consumer<ConstraintViolation<UserLoginForm>> errorsPutFunc = (cv) -> {
            String message = messageSource.getMessage(
                    cv.getMessageTemplate().replaceAll("[{}]", ""), null, locale
            );
            errors.put(cv.getPropertyPath().toString(), message);
        };
        List.of("email", "password").forEach((property) -> {
            validator.validateProperty(userLoginForm, property).stream().findFirst().ifPresent(errorsPutFunc);
        });

        if (errors.isEmpty()) {
            errors.put("email", "");
            errors.put("password", messageSource.getMessage("login.fail", null, locale));
        }

        return errors;
    }
    // -------------------------------------------------------
}
