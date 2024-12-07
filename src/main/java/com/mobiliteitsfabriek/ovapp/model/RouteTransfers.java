package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

public class RouteTransfers {
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime plannedDepartureDateTime;
    private LocalDateTime plannedArrivalDateTime;
    private int plannedDurationMinutes;

    private String departureLocationDetails;
    private String arrivalLocationDetails;

    private String transportType;
    private String transportName;
    private String transportDirection;

    public RouteTransfers(String departureLocation, String arrivalLocation, String departureLocationDetails, String arrivalLocationDetails, LocalDateTime plannedDepartureDateTime, LocalDateTime plannedArrivalDateTime, String transportType, String transportName, String transportDirection) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureLocationDetails = departureLocationDetails;
        this.arrivalLocationDetails = arrivalLocationDetails;
        this.plannedDepartureDateTime = plannedDepartureDateTime;
        this.plannedArrivalDateTime = plannedArrivalDateTime;
        this.plannedDurationMinutes = UtilityFunctions.getMinutesDifference(plannedDepartureDateTime, plannedArrivalDateTime);
        this.transportType = transportType;
        this.transportName = transportName;
        this.transportDirection = transportDirection;
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
        return UtilityFunctions.formatLocationAndDetails(departureLocation, departureLocationDetails);
    }

    public String getArrivalLocationAndDetails() {
        return UtilityFunctions.formatLocationAndDetails(arrivalLocation, arrivalLocationDetails);
    }

    public LocalDateTime getPlannedDepartureDateTime() {
        return plannedDepartureDateTime;
    }

    public LocalDateTime getPlannedArrivalDateTime() {
        return plannedArrivalDateTime;
    }

    public int getPlannedDurationMinutes() {
        return plannedDurationMinutes;
    }

    public String getCombinedTransport() {
        return UtilityFunctions.formatTransport(transportName, transportType, transportDirection);
    }

    public String getTransportType() {
        return transportType;
    }

    public String getTransportName() {
        return transportName;
    }
}
