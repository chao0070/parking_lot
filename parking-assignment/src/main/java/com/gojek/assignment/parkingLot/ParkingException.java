package com.gojek.assignment.parkingLot;

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
