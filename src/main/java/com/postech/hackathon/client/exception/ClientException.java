package com.postech.hackathon.client.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private Integer status;

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
