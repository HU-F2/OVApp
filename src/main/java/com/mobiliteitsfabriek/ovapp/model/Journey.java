package com.mobiliteitsfabriek.ovapp.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a complete journey from origin to destination, potentially consisting of multiple legs.
 * A journey contains information about departure and arrival times, locations, and the individual
 * segments (legs) that make up the complete trip.
 */
@Data
public class Journey {
    /** The station where the journey begins */
    private Station origin;

    /** The station where the journey ends */
    private Station destination;

    /** The date and time when the journey begins */
    private LocalDateTime departureTime;

    /** The date and time when the journey ends */
    private LocalDateTime arrivalTime;

    /** The list of individual segments that make up this journey */
    private List<JourneyLeg> legs;

    /** The total duration of the journey in minutes */
    private int duration;
} 