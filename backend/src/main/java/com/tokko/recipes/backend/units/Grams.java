package com.tokko.recipes.backend.units;


public class Grams extends Unit {

    @Override
    public Number convertToBase(Number quantity) {
        return quantity;
    }

    @Override
    public Number convertToThis(Number quantity) {
        return quantity;
    }

    @Override
    public Unit upscale() {
        return new HectoGram();
    }

    @Override
    public String getSuffix() {
        return "g";
    }
}
