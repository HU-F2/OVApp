package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ApiRequestException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingPayloadException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class GeneralService {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = createObjectMapper();
    }

    static String sendApiRequest(String baseUrl, String queryParams) {
        try {
            String fullUrl = MessageFormat.format("{0}{1}", baseUrl, queryParams);
            URL url = new URI(fullUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", GlobalConfig.API_KEY);
            connection.setRequestProperty("Accept", "application/json;q=0.9");

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return GeneralService.getResponseAsString(connection);
            } else {
                throw new ApiRequestException(connection.getResponseCode(), connection.getResponseMessage());
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } catch (ApiRequestException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    static void writeResponseToFile(String filePath, String response) {
        try {
            JsonNode rootNode = GeneralService.getObjectMapper().readTree(response);

            JsonNode payloadNode = rootNode.get("payload");
            if (UtilityFunctions.checkEmpty(payloadNode)) {
                throw new MissingPayloadException();
            }

            File outputFile = new File(filePath);
            GeneralService.getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(outputFile, payloadNode);

            System.out.println(TranslationHelper.get("io.fileSaved", outputFile.getAbsolutePath()));
        } catch (IOException | MissingPayloadException e) {
            System.err.println(e.getMessage());
        }
    }

    static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static String getResponseAsString(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String response = in.lines().collect(Collectors.joining());
            return response;
        }
    }

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
