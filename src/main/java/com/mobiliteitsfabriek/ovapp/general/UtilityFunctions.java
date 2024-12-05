package com.mobiliteitsfabriek.ovapp.general;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.StopV3;

public class UtilityFunctions {
    // Calculations
    public static Integer getTransferBetweenStopsInMinutes(StopV3 stop1, StopV3 stop2) {
        if (stop1 == null || stop2 == null) {
            throw new IllegalArgumentException("getTransferBetweenStopsInMinutes, a stop can't be null.");
        }
        return (int) Duration.between(stop1.getDepartureTime(), stop2.getArrivalTime()).toMinutes();
    }

    public static int getMinutesDifference(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return (int) Duration.between(dateTime1, dateTime2).toMinutes();
    }

    // formattering
    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.DATE_TIME_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.TIME_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatDuration(int durationMinutes) {
        long hours = durationMinutes / 60;
        long minutes = durationMinutes % 60;
        return String.format("%02d:%02d uur", hours, minutes);
    }

    public static String formatValueAsCurrency(double value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(GlobalConfig.NETHERLANDS_LOCAL);

        return currencyFormatter.format(value);
    }

    // Checking
    public static boolean checkEmpty(String valueToCheck) {
        return valueToCheck == null || valueToCheck.isBlank();
    }

    public static boolean checkEmpty(Integer valueToCheck) {
        return valueToCheck == null;
    }
}
