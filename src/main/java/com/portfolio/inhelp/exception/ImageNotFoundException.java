package com.portfolio.inhelp.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(long id) {
        super("Could not find comment with id: " + id);
    }
}
