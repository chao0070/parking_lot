package com.gojek.assignment.parkingLot;

public class ParkingException extends Exception {
    private ParkingError parkingError;

    public ParkingException(String message, ParkingError parkingError) {
        super(message);
        this.parkingError = parkingError;
    }

    public ParkingException(String message, Throwable cause, ParkingError parkingError) {
        super(message, cause);
        this.parkingError = parkingError;
    }

    public ParkingException(Throwable cause, ParkingError parkingError) {
        super(cause);
        this.parkingError = parkingError;
    }

    public ParkingError getParkingError() {
        return parkingError;
    }
}
