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
public class HistoryException extends RuntimeException{
    private ErrorCode errorCode;
    public static ErrorCode HISTORY_NOT_FOUND = new ErrorCode("History Not Found","History-01", HttpStatus.BAD_REQUEST);
}
