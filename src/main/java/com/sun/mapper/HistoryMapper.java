package com.sun.mapper;

import com.sun.common.DateCommon;
import com.sun.dto.HistoryDto;
import com.sun.dto.HistoryRequestDto;
import com.sun.entity.History;
import com.sun.entity.HistoryDetail;
import com.sun.entity.User;

import java.util.List;

public class HistoryMapper {

    public static HistoryRequestDto mapHistory(History history, List<HistoryDetail> historyDetail, User user) {
        HistoryRequestDto historyRequestDto = new HistoryRequestDto();
        historyRequestDto.setHistoryId(history.getId());
        historyRequestDto.setNumberOfBook(historyDetail.size());
        historyRequestDto.setStatus(String.valueOf(history.getStatus()));
        if (history.getBorrowDate() == null) {
            historyRequestDto.setBorrowDate("None");
        } else {
            historyRequestDto.setBorrowDate(DateCommon.Format(history.getBorrowDate()));
        }
        if (history.getReceivedDate() == null) {
            historyRequestDto.setReceivedDate("None");
        } else {
            historyRequestDto.setReceivedDate(DateCommon.Format(history.getReceivedDate()));
        }
        historyRequestDto.setUserName(user.getName());
        historyRequestDto.setEmail(user.getEmail());
        return historyRequestDto;
    }

    public static HistoryDto mapHistoryDto(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(history.getId());
        if (history.getBorrowDate() == null) {
            historyDto.setBorrowDate("None");
        } else {
            historyDto.setBorrowDate(DateCommon.Format(history.getBorrowDate()));
        }
        if (history.getReceivedDate() == null) {
            historyDto.setReceivedDate("None");
        } else {
            historyDto.setReceivedDate(DateCommon.Format(history.getReceivedDate()));
        }
        historyDto.setStatus(String.valueOf(history.getStatus()));
        return historyDto;
    }
}
