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
public class BookException extends RuntimeException{
    private ErrorCode errorCode;

    public static final ErrorCode BOOK_NOT_FOUND = new ErrorCode("Book Not Found","Book_01", HttpStatus.BAD_REQUEST);
}
