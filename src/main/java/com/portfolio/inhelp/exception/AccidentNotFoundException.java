package com.portfolio.inhelp.exception;

public class AccidentNotFoundException extends RuntimeException{

    public AccidentNotFoundException(long id) {
        super("Could not find accident with id:" + id);
    }
}
