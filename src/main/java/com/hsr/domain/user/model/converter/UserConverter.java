package com.hsr.domain.user.model.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.hsr.domain.user.model.User;
import com.hsr.domain.user.model.UserView;

public class UserConverter {

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
                    user.getGender() == null ? "" : user.getGender().toString(),
                    formatDate(user.getBirthday()),
                    user.getIntroduction(),
                    formatDateTime(user.getCreatedAt())
                );
        return userView;
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
}
