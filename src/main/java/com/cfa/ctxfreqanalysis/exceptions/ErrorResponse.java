package com.cfa.ctxfreqanalysis.exceptions;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;
}
