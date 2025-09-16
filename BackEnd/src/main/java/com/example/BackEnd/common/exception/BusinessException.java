package com.example.BackEnd.common.exception;

import com.example.BackEnd.common.enums.error_codes.Errors;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Errors errors;

    public BusinessException(Errors errors, Throwable cause) {
        super(errors.getMessage(), cause);
        this.errors = errors;
    }

    public BusinessException(Errors errors) {
        super(errors.getMessage());
        this.errors = errors;
    }
}
