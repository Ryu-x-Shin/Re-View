package com.example.BackEnd.common.Response;

import com.example.BackEnd.common.enums.error_codes.Errors;
import com.example.BackEnd.common.enums.success_messages.Success;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static <T> ResponseEntity<SuccessResponse<T>> success(Success response, T data) {
        return ResponseEntity.status(response.getCode())
                .body(SuccessResponse.of(response.getCode(), response.getMessage(), data));
    }

    public static ResponseEntity<SuccessResponse<Void>> success(Success response) {
        return ResponseEntity.status(response.getCode())
                .body(SuccessResponse.of(response.getCode(), response.getMessage(), null));
    }

    public static ResponseEntity<ErrorResponse> fail(Errors response) {
        return ResponseEntity.status(response.getCode())
                .body(ErrorResponse.of(response.getCode(), response.getMessage()));
    }
}
