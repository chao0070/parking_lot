package com.gojek.assignment.parkingLot;

import com.gojek.assignment.vehicle.Car;

import java.util.Map;

public class ParkingLot {
    private final int capacity;

    public ParkingLot(int capacity) throws ParkingException {
        if (capacity <= 0) {
            throw new ParkingException(ParkingError.PL_INVALID_CAPACITY.getError(), ParkingError.PL_INVALID_CAPACITY);
        }
        this.capacity = capacity;
    }
}
