package org.eventhub.main.exception;

public class BadSearchRequestException extends RuntimeException {
    public BadSearchRequestException() {
    }

    public BadSearchRequestException(String message) {
        super(message);
    }
}
