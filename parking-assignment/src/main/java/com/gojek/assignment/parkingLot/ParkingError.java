package com.gojek.assignment.parkingLot;

/**
 * ParkingError encapsulates the error which can be redirected to console. Errors are self explanatory
 */
public enum ParkingError {
    PL_INVALID_CAPACITY("Capacity needs to be greater than 0"),
    PL_SLOT_INVALID("The slot is invalid"),
    PL_SLOT_NOCAR("No car in slot to unpark"),
    PL_FULL("Sorry, parking lot is full"),
    PL_CAR_PARKED_REG_DUB("Already a car parked with same registration number"),
    PL_CAR_NOT_FOUND("Not found");

    private String error;

    ParkingError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
