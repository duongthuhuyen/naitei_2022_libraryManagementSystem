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
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    public static final ErrorCode USER_NOT_FOUND = new ErrorCode("User Not Found","User-01", HttpStatus.BAD_REQUEST);
}
