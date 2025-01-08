package com.mobiliteitsfabriek.ovapp.model;

public class SearchManagement {
    private static Search currentSearch = null;

    public static void setCurrentSearch(Search value) {
        currentSearch = value;
    }

    public static Search getCurrentSearch() {
        return currentSearch;
    }
}
