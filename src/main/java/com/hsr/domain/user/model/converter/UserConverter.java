package com.hsr.domain.user.model.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.hsr.domain.user.form.UserEditForm;
import com.hsr.domain.user.form.UserSignupForm;
import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.UserView;

@Component
public class UserConverter {

    private static MessageSource messageSource;
    @Autowired
    private UserConverter(MessageSource messageSource) {
        UserConverter.messageSource = messageSource;
    }

    /**
     * convert user to user view.
     * @param user
     * @return user view
     */
    public static UserView toView(User user) {
        UserView userView =
                new UserView(
                    user.getEmail(),
                    user.getName(),
                    convertGender(user.getGender()),
                    formatDate(user.getBirthday()),
                    user.getIntroduction(),
                    formatDateTime(user.getCreatedAt())
                );
        return userView;
    }

    /**
     * convert user to user edit form.
     * @param user
     * @return user edit form
     */
    public static UserEditForm toEditForm(User user) {
        UserEditForm userEditForm =
                new UserEditForm(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    "",
                    "",
                    user.getGender(),
                    user.getBirthday() != null
                        ? user.getBirthday().toString()
                        : null,
                    user.getIntroduction()
                );
        return userEditForm;
    }

    /**
     * convert user sign up form to user.
     * @param user sign up form
     * @return user
     */
    public static User toModel(UserSignupForm userSignupForm) {
        User user =
                new User(
                    null,
                    userSignupForm.getEmail(),
                    userSignupForm.getName(),
                    userSignupForm.getPassword(),
                    null,
                    null,
                    null,
                    null,
                    null
                );
        return user;
    }

    /**
     * convert user edit form to user.
     * @param user edit form
     * @return user
     */
    public static User toModel(UserEditForm userEditForm) {
        User user =
                new User(
                    userEditForm.getId(),
                    userEditForm.getEmail(),
                    userEditForm.getName(),
                    userEditForm.getPassword(),
                    userEditForm.getGender(),
                    toLocalDate(userEditForm.getBirthday()),
                    userEditForm.getIntroduction(),
                    null,
                    null
                );
        return user;
    }

    private static String convertGender(Integer gender) {
        if (gender == null) {
            return "-";
        }

        String[] genders = messageSource.getMessage("user.gender.array", null, Locale.getDefault()).split(", ");
        if (gender < 0 || gender > genders.length - 1) {
            return "-";
        }
        return genders[gender];
    }

    private static String formatDate(LocalDate date) {
        if (date == null) {
            return "-";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.format(formatter);
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return dateTime.format(formatter);
    }

    private static LocalDate toLocalDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            return LocalDate.parse(dateString);
        } catch(Exception e) {
            return null;
        }
    }
}
