package com.postech.hackathon.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private Integer status;

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
