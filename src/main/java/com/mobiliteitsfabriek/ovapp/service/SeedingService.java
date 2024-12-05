package com.mobiliteitsfabriek.ovapp.service;

import java.text.MessageFormat;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;

public class SeedingService {
    private static final String BASE_URL = "https://gateway.apiportal.ns.nl/nsapp-stations/v3";

    public void getAllStations() {
        boolean includeNonPlannableStations = true;
        getAllStations(includeNonPlannableStations);
    }

    public void getAllStations(boolean includeNonPlannableStations) {
        String queryParams = MessageFormat.format("?includeNonPlannableStations={0}", includeNonPlannableStations);
        String data = GeneralService.sendApiRequest(BASE_URL, queryParams);

        if (data == null) {
            return;
        }

        GeneralService.writeResponseToFile(GlobalConfig.FILE_PATH_STATIONS, data);
    }
}
