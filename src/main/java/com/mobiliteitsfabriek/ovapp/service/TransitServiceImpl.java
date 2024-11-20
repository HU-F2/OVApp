package com.mobiliteitsfabriek.ovapp.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mobiliteitsfabriek.ovapp.exceptions.RuntimeParseException;
import com.mobiliteitsfabriek.ovapp.model.Journey;
import com.mobiliteitsfabriek.ovapp.model.Station;

/**
 * Implementation of the TransitService interface that handles communication with the transit API.
 * Provides functionality for searching stations, planning journeys, and retrieving departure information.
 */
public class TransitServiceImpl implements TransitService {
    /**
     * HTTP client used for making API requests
     */
    private final HttpClient httpClient;

    /**
     * Object mapper for JSON serialization/deserialization
     */
    private final ObjectMapper objectMapper;

    /**
     * Base URL for the transit API endpoints
     */
    private final String apiBaseUrl;

    /**
     * Authentication key for accessing the transit API
     */
    private final String apiKey;

    /**
     * Constructs a new TransitServiceImpl with the specified API configuration.
     *
     * @param apiBaseUrl the base URL of the transit API
     * @param apiKey     the authentication key for the API
     */
    public TransitServiceImpl(String apiBaseUrl, String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        this.apiBaseUrl = apiBaseUrl;
        this.apiKey = apiKey;
    }

    /**
     * Searches for stations matching the provided query string.
     *
     * @param query the search term to find matching stations
     * @return a CompletableFuture containing a list of matching stations
     * @throws RuntimeException if there is an error parsing the API response
     */
    @Override
    public CompletableFuture<List<Station>> searchStations(String query) {
        final String url = String.format("%s/locations?q=%s", apiBaseUrl, query);
        return sendRequest(url, "GET")
                .thenApply(response -> {
                    try {
                        return objectMapper.readValue(
                                response,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Station.class));
                    } catch (Exception e) {
                        throw new RuntimeParseException("stations", e);
                    }
                });
    }

    /**
     * Plans a journey between two stations at a specified date and time.
     *
     * @param from     the departure station
     * @param to       the destination station
     * @param dateTime the desired departure or arrival time
     * @return a CompletableFuture containing a list of possible journeys
     * @throws RuntimeException if there is an error parsing the API response
     */
    @Override
    public CompletableFuture<List<Journey>> planJourney(
            Station from,
            Station to,
            LocalDateTime dateTime) {
        final String url = String.format("%s/journey?from=%s&to=%s&datetime=%s",
                apiBaseUrl, from.getId(), to.getId(), dateTime);
        return sendRequest(url, "GET")
                .thenApply(response -> {
                    try {
                        return objectMapper.readValue(
                                response,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Journey.class));
                    } catch (Exception e) {
                        throw new RuntimeParseException("journey", e);
                    }
                });
    }

    /**
     * Retrieves upcoming departures from a specific station.
     *
     * @param station  the station to get departures for
     * @param dateTime the time from which to retrieve departures
     * @return a CompletableFuture containing a list of upcoming departures
     * @throws RuntimeException if there is an error parsing the API response
     */
    @Override
    public CompletableFuture<List<Journey>> getDepartures(
            Station station,
            LocalDateTime dateTime) {
        final String url = String.format("%s/departures?station=%s&datetime=%s",
                apiBaseUrl, station.getId(), dateTime);
        return sendRequest(url, "GET")
                .thenApply(response -> {
                    try {
                        return objectMapper.readValue(
                                response,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Journey.class));
                    } catch (Exception e) {
                        throw new RuntimeParseException("departures", e);
                    }
                });
    }

    /**
     * Sends an HTTP request to the transit API.
     *
     * @param url    the complete URL for the API endpoint
     * @param method the HTTP method to use (e.g., "GET")
     * @return a CompletableFuture containing the response body as a String
     */
    private CompletableFuture<String> sendRequest(String url, String method) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}