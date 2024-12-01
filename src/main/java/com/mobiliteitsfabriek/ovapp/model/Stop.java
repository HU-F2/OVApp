package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Stop {
    private final String stationName;
    private final String platform;   
    private final LocalDateTime time; // Tijd van aankomst/vertrek
    private final String transportType; 
    private final String destination; 

    public Stop(String stationName, String platform, LocalDateTime time, String transportType, String destination) {
        this.stationName = stationName;
        this.platform = platform;
        this.time = time;
        this.transportType = transportType;
        this.destination = destination;
    }

    public String getStationName() {
        return stationName;
    }

    public String getPlatform() {
        return platform;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTransportType() {
        return transportType;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return stationName + " (Spoor/Halte: " + platform + ") om " + time + " met " + transportType + " naar " + destination;
    }
}
