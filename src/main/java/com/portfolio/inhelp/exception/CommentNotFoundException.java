package com.portfolio.inhelp.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(long id) {
        super("Could not find comment with id: " + id);
    }
}
