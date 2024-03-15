package com.postech.hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {

    private List<String> errors;
}