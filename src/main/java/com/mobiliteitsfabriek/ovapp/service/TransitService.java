package com.mobiliteitsfabriek.ovapp.service;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.model.Journey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TransitService {
    CompletableFuture<List<Station>> searchStations(String query);
    
    CompletableFuture<List<Journey>> planJourney(
        Station from, 
        Station to, 
        LocalDateTime dateTime
    );
    
    CompletableFuture<List<Journey>> getDepartures(
        Station station, 
        LocalDateTime dateTime
    );
} 