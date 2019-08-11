package com.gojek.assignment.vehicle;

public class Car {
    private final int slot;

    private final String reg;

    private final String color;

    public Car(int slot, String reg, String color) {
        this.slot = slot;
        this.reg = reg;
        this.color = color;
    }

    public int getSlot() {
        return slot;
    }

    public String getReg() {
        return reg;
    }

    public String getColor() {
        return color;
    }
}
