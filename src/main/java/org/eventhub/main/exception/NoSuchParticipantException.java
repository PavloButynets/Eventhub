package org.eventhub.main.exception;

public class NoSuchParticipantException extends RuntimeException{
    public NoSuchParticipantException() {
        super();
    }

    public NoSuchParticipantException(String message) {
        super(message);
    }
}
