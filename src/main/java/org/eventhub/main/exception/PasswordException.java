package org.eventhub.main.exception;

public class PasswordException extends RuntimeException{
    public  PasswordException() {    }
    public  PasswordException(String message) {
        super(message);
    }
}