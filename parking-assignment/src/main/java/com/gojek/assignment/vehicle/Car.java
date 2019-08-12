package com.gojek.assignment.vehicle;

import java.util.Objects;

/**
 * DTO class to keep info about cars to be parked
 */
public class Car {
    private final String reg;

    private final String color;

    public Car(String reg, String color) {
        this.reg = reg;
        this.color = color;
    }

    public String getReg() {
        return reg;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return reg.equals(car.reg) &&
                color.equals(car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reg, color);
    }
}
