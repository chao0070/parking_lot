package com.gojek.assignment.parkingLot;

import com.gojek.assignment.vehicle.Car;

import java.util.Objects;

/**
 * DTO object to identify Slots in  parking lot. Car field is null untill parking of car
 */
public class Slot {
    private Integer id;

    private Car parkedCar;

    public Slot(Integer id) {
        this.id = id;
        this.parkedCar = null;
    }

    public Integer getId() {
        return id;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void setParkedCar(Car parkedCar) {
        this.parkedCar = parkedCar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return id.equals(slot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
