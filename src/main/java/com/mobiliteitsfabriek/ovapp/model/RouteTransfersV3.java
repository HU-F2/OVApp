package com.mobiliteitsfabriek.ovapp.model;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

public class RouteTransfersV3 {
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int durationMinutes;

    private String departureLocationDetails;
    private String arrivalLocationDetails;

    private String transportType;
    private String transportName;
    private String transportDirection;

    public RouteTransfersV3(String departureLocation, String arrivalLocation, String departureLocationDetails, String arrivalLocationDetails, LocalDateTime departureTime, LocalDateTime arrivalTime, String transportType, String transportName, String transportDirection) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureLocationDetails = departureLocationDetails;
        this.arrivalLocationDetails = arrivalLocationDetails;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationMinutes = UtilityFunctions.getMinutesDifference(departureTime, arrivalTime);
        this.transportType = transportType;
        this.transportName = transportName;
        this.transportDirection = transportDirection;
    }

    private String getCombinedLocationAndDetails(String location, String details) {
        return MessageFormat.format("{0}, {1}", location, details);
    }

    // Getters And Setters
    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDepartureLocationDetails() {
        return departureLocationDetails;
    }

    public String getArrivalLocationDetails() {
        return arrivalLocationDetails;
    }

    public String getDepartureLocationAndDetails() {
        if (departureLocationDetails == null || departureLocationDetails.isEmpty()) {
            return departureLocation;
        }
        return getCombinedLocationAndDetails(departureLocation, departureLocationDetails);
    }

    public String getArrivalLocationAndDetails() {
        if (arrivalLocationDetails == null || arrivalLocationDetails.isEmpty()) {
            return arrivalLocation;
        }
        return getCombinedLocationAndDetails(arrivalLocation, arrivalLocationDetails);
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getCombinedTransport() {
        if (!UtilityFunctions.checkEmpty(transportType) && !UtilityFunctions.checkEmpty(transportDirection)) {
            return MessageFormat.format("{1}, {2} ({0})", transportType, transportName, transportDirection);
        } else if (UtilityFunctions.checkEmpty(transportType) && !UtilityFunctions.checkEmpty(transportDirection)) {
            return MessageFormat.format("{0}, {1}", transportName, transportDirection);
        } else if (!UtilityFunctions.checkEmpty(transportType) && UtilityFunctions.checkEmpty(transportDirection)) {
            return MessageFormat.format("{0} ({1})", transportName, transportType);
        }
        return this.transportName;        
    }

    public String getTransportType() {
        return transportType;
    }

    public String getTransportName() {
        return transportName;
    }
}
