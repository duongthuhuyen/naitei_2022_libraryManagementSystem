package com.sun.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
    private int userID;
    private int bookID;
    private LocalDateTime borrowDate;
}
