package org.eventhub.main.exception;

public class NullDtoReferenceException extends RuntimeException{
    public NullDtoReferenceException() {
    }
    public NullDtoReferenceException(String message) {
        super(message);
    }
}
