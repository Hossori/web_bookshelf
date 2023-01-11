package com.hsr.domain.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class DomainConverter {
    protected static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(formatter);
    }

    protected static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    protected static LocalDateTime toLocalDateTime(Long epochSecond, ZoneId zoneId) {
        if (epochSecond == null) {
            return null;
        }
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return Instant.ofEpochSecond(epochSecond).atZone(zoneId).toLocalDateTime();
    }

    protected static LocalDate toLocalDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            return LocalDate.parse(dateString);
        } catch(Exception e) {
            return null;
        }
    }

    protected static LocalDate toLocalDate(Long epochSecond, ZoneId zoneId) {
        if (epochSecond == null) {
            return null;
        }
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return Instant.ofEpochSecond(epochSecond).atZone(zoneId).toLocalDate();
    }
}
