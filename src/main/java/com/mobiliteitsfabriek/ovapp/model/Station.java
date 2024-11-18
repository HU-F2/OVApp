package com.mobiliteitsfabriek.ovapp.model;

import lombok.Data;
import lombok.Setter;

/**
 * Represents a transit station or stop in the transportation network.
 * Contains information about the station's location, type, and accessibility features.
 */
@Data
public class Station {
    /**
     * Unique identifier for the station
     */
    private String id;

    /**
     * Name of the station
     */
    private String name;

    /**
     * Type of station (e.g., "train", "bus", "metro")
     */
    private String type;

    /**
     * Geographical latitude coordinate of the station
     */
    private double latitude;

    /**
     * Geographical longitude coordinate of the station
     */
    private double longitude;

    /**
     * Indicates whether the station has elevator facilities
     * -- SETTER --
     * Sets the elevator availability status for the station.
     */
    @Setter
    private boolean hasElevator;

    /**
     * Indicates whether the station has accessible platforms
     * -- SETTER --
     * Sets the platform accessibility status for the station.
     */
    @Setter
    private boolean accessiblePlatform;

    /**
     * Checks if the station has elevator facilities.
     *
     * @return true if the station has an elevator, false otherwise
     */
    public boolean hasElevator() {
        return hasElevator;
    }

    /**
     * Checks if the station has accessible platforms.
     *
     * @return true if the station has accessible platforms, false otherwise
     */
    public boolean hasAccessiblePlatform() {
        return accessiblePlatform;
    }
}