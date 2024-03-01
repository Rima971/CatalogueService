package com.swiggy.catalogue.entities;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class GenericHttpResponse {
    int statusCode;
    HttpStatus status;
    String message;
    Object data;

    private GenericHttpResponse(int statusCode, HttpStatus status, String message, Object data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<GenericHttpResponse> create(HttpStatus httpStatus, String message, Object data){
        return new ResponseEntity<>(new GenericHttpResponse(httpStatus.value(), httpStatus, message, data), httpStatus);
    }
}
