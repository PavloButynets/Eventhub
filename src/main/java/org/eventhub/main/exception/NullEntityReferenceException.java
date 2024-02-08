package org.eventhub.main.exception;

public class NullEntityReferenceException extends RuntimeException{
    public NullEntityReferenceException() {
        super();
    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}
