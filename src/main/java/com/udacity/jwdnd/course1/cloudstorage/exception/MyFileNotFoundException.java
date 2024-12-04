package com.udacity.jwdnd.course1.cloudstorage.exception;

public class MyFileNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
