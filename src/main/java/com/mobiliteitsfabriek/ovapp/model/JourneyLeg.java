package com.mobiliteitsfabriek.ovapp.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Represents a single leg of a journey in public transportation.
 * A leg is a portion of the journey that uses one mode of transport between two stations.
 */
@Data
public class JourneyLeg {
    /** The mode of transportation (e.g., "train", "bus", "walk") */
    private String mode;

    /** The station where this leg of the journey begins */
    private Station from;

    /** The station where this leg of the journey ends */
    private Station to;

    /** The scheduled departure time from the origin station */
    private LocalDateTime departureTime;

    /** The scheduled arrival time at the destination station */
    private LocalDateTime arrivalTime;

    /** The line or service number of the transport (e.g., train number, bus route) */
    private String lineNumber;

    /** The company or organization operating this transport service */
    private String operator;
} 