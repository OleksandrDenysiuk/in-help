package com.portfolio.inhelp.exception.advice;

import com.portfolio.inhelp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccidentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accidentNotFoundHandler(AccidentNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String commentNotFoundHandler(CommentNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String imageNotFoundHandler(ImageNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NewsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String newsNotFoundHandler(NewsNotFoundException e){
        return e.getMessage();
    }
}
