package org.eventhub.main.exception;

public class AccessIsDeniedException extends RuntimeException{
    public AccessIsDeniedException() {    }

    public AccessIsDeniedException(String message) {
        super(message);
    }
}
