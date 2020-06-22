package com.portfolio.inhelp.exception;

public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(long id) {
        super("Could not find news with id: " + id);
    }
}
