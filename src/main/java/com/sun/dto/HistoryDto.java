package com.sun.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDto {
    private int id;
    private String borrowDate;
    private String receivedDate;
    private String status;
}
