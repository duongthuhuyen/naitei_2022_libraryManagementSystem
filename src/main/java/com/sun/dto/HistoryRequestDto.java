package com.sun.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRequestDto {
    private int historyId;
    private int numberOfBook;
    private String borrowDate;
    private String receivedDate;
    private String status;
    private String userName;
    private String email;
}
