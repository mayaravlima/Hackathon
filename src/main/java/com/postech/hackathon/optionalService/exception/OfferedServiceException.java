package com.postech.hackathon.optionalService.exception;

import lombok.Getter;

@Getter
public class OfferedServiceException extends RuntimeException {
    private Integer status;

    public OfferedServiceException(String message) {
        super(message);
    }

    public OfferedServiceException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
