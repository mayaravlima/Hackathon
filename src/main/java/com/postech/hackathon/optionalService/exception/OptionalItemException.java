package com.postech.hackathon.optionalService.exception;

import lombok.Getter;

@Getter
public class OptionalItemException extends RuntimeException {
    private Integer status;

    public OptionalItemException(String message) {
        super(message);
    }

    public OptionalItemException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
