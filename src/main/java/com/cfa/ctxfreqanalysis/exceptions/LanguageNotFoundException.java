package com.cfa.ctxfreqanalysis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LanguageNotFoundException extends Exception{
    public LanguageNotFoundException(String exception){
        super(exception);
    }
}
