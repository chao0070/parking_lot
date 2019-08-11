package com.gojek.assignment.parkingLot;

public enum ParkingError {
    PL_INVALID_CAPACITY("Capacity needs to be greater than 0."),
    PL_SLOT_NOCAR("No car in slot to unpark."),
    PL_FULL("All slots are full in parking. No place to park.");

    private String error;

    ParkingError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ParkingError{" +
                "error='" + error + '\'' +
                '}';
    }
}
