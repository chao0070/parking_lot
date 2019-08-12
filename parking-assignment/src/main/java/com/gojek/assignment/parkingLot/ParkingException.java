package com.gojek.assignment.parkingLot;

/**
 * Custom Exception to handle exceptions for ParkingLot. The exceptions are based on ParkingError which have the encapsulated error strings
 */
public class ParkingException extends Exception {
    private ParkingError parkingError;

    public ParkingException(String message, ParkingError parkingError) {
        super(message);
        this.parkingError = parkingError;
    }

    public ParkingError getParkingError() {
        return parkingError;
    }
}
