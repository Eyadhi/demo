package com.example.demo.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.model.ApiResponse;

@ControllerAdvice
public class GlobalResponseHandler {

    @ResponseBody
    public <T> ResponseEntity<ApiResponse<T>> wrapResponse(T body) {
        ApiResponse<T> response = new ApiResponse<>(true, 200, body);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        ApiResponse<String> response = new ApiResponse<>(false, 500, e.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}
