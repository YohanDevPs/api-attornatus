package com.example.attornatus.attornatus.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ElementRepeatedException extends RuntimeException {

    public ElementRepeatedException() {
        super("It is not allowed to persist a repeated object");
    }

    public ElementRepeatedException(String ex) {
        super(ex);
    }
}
