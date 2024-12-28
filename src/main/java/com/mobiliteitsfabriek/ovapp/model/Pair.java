package com.mobiliteitsfabriek.ovapp.model;

public class Pair<A> {
    private final A firstObject;
    private final A secondObject;

    public Pair(A firstObject, A secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    public A getFirstObject() {
        return firstObject;
    }

    public A getSecondObject() {
        return secondObject;
    }
}
