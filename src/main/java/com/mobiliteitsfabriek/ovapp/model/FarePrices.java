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

    public void setFirstClassPriceInCents(int firstClassPriceInCents) {
        this.firstClassPriceInCents = firstClassPriceInCents;
    }

    public int getSecondClassPriceInCents() {
        return secondClassPriceInCents;
    }

    public void setSecondClassPriceInCents(int secondClassPriceInCents) {
        this.secondClassPriceInCents = secondClassPriceInCents;
    }



}
