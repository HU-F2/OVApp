package com.mobiliteitsfabriek.ovapp.general;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.FarePrices;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.model.User;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class UtilityFunctions {
    // Calculations
    public static int getMinutesDifference(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (UtilityFunctions.checkEmpty(dateTime1) || UtilityFunctions.checkEmpty(dateTime2)) {
            throw new IllegalArgumentException(TranslationHelper.get("error.datetime.null"));
        }
        return (int) Duration.between(dateTime1, dateTime2).toMinutes();
    }

    // formattering
    public static LocalDateTime getDateTimeFromNS(String dateTime) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, GlobalConfig.NS_DATE_TIME_FORMATTER);
    }

    private static LocalTime getLocalTime(String timeString) throws DateTimeParseException, IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.TIME_FORMAT);
        return LocalTime.parse(timeString, formatter);
    }

    public static String getDateTimeRFC3339Format(LocalDate selectedDate, String selectedTime) throws DateTimeParseException, IllegalArgumentException {
        LocalTime selectedLocalTime = UtilityFunctions.getLocalTime(selectedTime);
        LocalDateTime localDateTime = LocalDateTime.of(selectedDate, selectedLocalTime);

        ZonedDateTime amsterdamZonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Amsterdam"));

        // Format to RFC3339
        DateTimeFormatter rfc3339Formatter = DateTimeFormatter.ofPattern(GlobalConfig.RFC3339_TIME_FORMAT);
        return amsterdamZonedDateTime.format(rfc3339Formatter);
    }

    public static LocalDateTime getLocalDateFromRFC3339String(String value){        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.RFC3339_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
        return dateTime;
    }

    public static String formatDateTime(LocalDateTime dateTime) throws DateTimeException, IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.DATE_TIME_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatTime(LocalDateTime dateTime) throws DateTimeException, IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.TIME_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatDuration(int plannedDurationMinutes) {
        long hours = plannedDurationMinutes / 60;
        long minutes = plannedDurationMinutes % 60;
        return String.format("%02d:%02d uur", hours, minutes);
    }

    public static String formatValueAsCurrency(double value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(GlobalConfig.NETHERLANDS_LOCAL);
        return currencyFormatter.format(value);
    }

    public static String formatTransport(String transportName, String transportType, String transportDirection) {
        if (!UtilityFunctions.checkEmpty(transportType) && !UtilityFunctions.checkEmpty(transportDirection)) {
            return MessageFormat.format("{1}, {2} ({0})", transportType, transportName, transportDirection);
        } else if (!UtilityFunctions.checkEmpty(transportDirection)) {
            return MessageFormat.format("{0}, {1}", transportName, transportDirection);
        } else if (!UtilityFunctions.checkEmpty(transportType)) {
            return MessageFormat.format("{0} ({1})", transportName, transportType);
        }
        return transportName;
    }

    public static String formatLocationAndDetails(String location, String details) {
        if (UtilityFunctions.checkEmpty(details)) {
            return location;
        }
        return MessageFormat.format("{0}, {1}", location, details);
    }

    // helper
    public static ArrayList<String> toArrayList(String... strings) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, strings);
        return list;
    }

    public static String generateID() {
        return UUID.randomUUID().toString();
    }

    public static String encodePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(GlobalConfig.BCRYPT_STRENGTH, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        return encodedPassword;
    }

    // Checking
    public static boolean checkStringInArrayList(String valueToCheck, String... strings) {
        ArrayList<String> stringArrayList = UtilityFunctions.toArrayList(strings);
        return stringArrayList.stream().anyMatch(s -> s.equalsIgnoreCase(valueToCheck));
    }

    public static boolean checkEmpty(String valueToCheck) {
        return valueToCheck == null || valueToCheck.isBlank();
    }

    public static boolean checkEmpty(Integer valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(Double valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(LocalDateTime valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(JsonNode valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(Station valueToCheck) {
        return valueToCheck == null || UtilityFunctions.checkEmpty(valueToCheck.getId()) || UtilityFunctions.checkEmpty(valueToCheck.getName());
    }

    public static boolean checkEmpty(User valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(FarePrices valueToCheck) {
        return valueToCheck == null;
    }
}
