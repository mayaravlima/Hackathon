package com.postech.hackathon.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {

    private List<String> errors;
}