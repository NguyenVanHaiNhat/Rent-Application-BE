package org.example.rentapplicationbe.exception;

public class InvalidHouseStatusException extends RuntimeException {
    public InvalidHouseStatusException(String message) {
        super(message);
    }
}
