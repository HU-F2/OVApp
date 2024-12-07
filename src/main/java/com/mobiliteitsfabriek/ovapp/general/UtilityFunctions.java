package com.mobiliteitsfabriek.ovapp.general;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;
import com.mobiliteitsfabriek.ovapp.model.Station;

public class UtilityFunctions {
    // Calculations
    public static Integer getTransferTimeBetweenRouteTransfersInMinutes(RouteTransfers routeTransfer1, RouteTransfers routeTransfer2) {
        if (routeTransfer1 == null || routeTransfer2 == null) {
            throw new IllegalArgumentException("getTransferTimeBetweenRouteTransfersInMinutes, a routeTransfer can't be null.");
        }
        return (int) Duration.between(routeTransfer1.getPlannedDepartureDateTime(), routeTransfer2.getPlannedArrivalDateTime()).toMinutes();
    }

    public static int getMinutesDifference(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return (int) Duration.between(dateTime1, dateTime2).toMinutes();
    }

    // formattering
    public static LocalDateTime getDateTimeFromNS(String dateTime) {
        return LocalDateTime.parse(dateTime, GlobalConfig.NS_DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConfig.DATE_TIME_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatTime(LocalDateTime dateTime) {
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
        if (transportType != null && transportDirection != null) {
            return MessageFormat.format("{1}, {2} ({0})", transportType, transportName, transportDirection);
        } else if (transportDirection != null) {
            return MessageFormat.format("{0}, {1}", transportName, transportDirection);
        } else if (transportType != null) {
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

    // Checking
    public static boolean checkEmpty(String valueToCheck) {
        return valueToCheck == null || valueToCheck.isBlank();
    }

    public static boolean checkEmpty(Integer valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(Double valueToCheck) {
        return valueToCheck == null;
    }

    public static boolean checkEmpty(Station valueToCheck) {
        return valueToCheck == null || UtilityFunctions.checkEmpty(valueToCheck.getId()) || UtilityFunctions.checkEmpty(valueToCheck.getName());
    }
}
