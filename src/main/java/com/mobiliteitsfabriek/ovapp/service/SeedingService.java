package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SeedingService {
    public void getAllStations() {
        try {
            String baseUrl = "https://gateway.apiportal.ns.nl/nsapp-stations/v3";

            // String query = "";
            boolean includeNonPlannableStations = true;
            String countryCodes = "NL";
            int limit = 10;

            // Construct URL with parameters
            String apiUrl = String.format(
                    "%s?includeNonPlannableStations=%b&countryCodes=%s&limit=%d",
                    baseUrl, includeNonPlannableStations, countryCodes, limit);
            // String apiUrl = String.format(
            //     "%s?q=%s&includeNonPlannableStations=%b&countryCodes=%s&limit=%d",
            //     baseUrl, query, includeNonPlannableStations, countryCodes, limit
            // );

            URI uri = new URI(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "57e1df724be741f6bc8f926355646bd5");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String filePath = "src/main/resources/stations.json";
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON string into a JsonNode
                try {
                    JsonNode rootNode = objectMapper.readTree(response.toString());

                    // Access the 'payload' key
                    JsonNode payloadNode = rootNode.get("payload");
                    if (payloadNode != null) {
                        File outputFile = new File(filePath);
                        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, payloadNode);

                        System.out.println("Payload saved to " + outputFile.getAbsolutePath());
                    } else {
                        System.out.println("Key 'payload' not found in the JSON.");
                    }
                } catch (Exception e) {

                }

            } else {
                System.out.println("GET request failed.");
            }
        } catch (Exception e) {

        }
    }
}
