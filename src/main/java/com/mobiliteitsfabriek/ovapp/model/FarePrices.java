package com.mobiliteitsfabriek.ovapp.model;

public class FarePrices {
    private int firstClassPriceInCents;
    private int secondClassPriceInCents;

    public FarePrices(int firstClassPriceInCents, int secondClassPriceInCents) {
        this.firstClassPriceInCents = firstClassPriceInCents;
        this.secondClassPriceInCents = secondClassPriceInCents;
    }

    public int getFirstClassPriceInCents() {
        return firstClassPriceInCents;
    }

    public int getSecondClassPriceInCents() {
        return secondClassPriceInCents;
    }
}
