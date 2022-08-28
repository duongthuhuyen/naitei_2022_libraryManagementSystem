package com.sun.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestException extends RuntimeException{
    private ErrorCode errorCode;
    public static final ErrorCode SUCCESS = new ErrorCode("Create Request Success","Request-01", HttpStatus.CREATED);
    public static final ErrorCode CREATE_REQUEST_ERROR = new ErrorCode("Create Request Error","Request-02",HttpStatus.BAD_REQUEST);
    public static final ErrorCode DENIED_CREATE_REQUEST = new ErrorCode("Denied Create Request","Request-03",HttpStatus.BAD_REQUEST);
}
