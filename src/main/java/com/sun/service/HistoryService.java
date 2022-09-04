package com.sun.service;

import com.sun.dto.HistoryDto;
import com.sun.dto.HistoryRequestDto;
import com.sun.entity.Book;
import com.sun.entity.History;
import com.sun.entity.HistoryDetail;
import com.sun.entity.User;
import com.sun.mapper.HistoryMapper;
import com.sun.repository.HistoryDetailRepository;
import com.sun.repository.HistoryRepository;
import com.sun.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.transaction.Transactional;

@Service
@Transactional
public class HistoryService {
    Logger logger = Logger.getLogger(HistoryService.class.getName());
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryDetailRepository historyDetailRepository;
    @Autowired
    private UserRepository userRepository;

    public List<HistoryDto> getHistory(int userId){
        List<HistoryDto> historyDtos = new ArrayList<>();
        try{
            List<History> histories = historyRepository.findByUserId(userId);
            if(!histories.isEmpty()){
                for(History history:histories) {
                    historyDtos.add(HistoryMapper.mapHistoryDto(history));
                }}
        }catch (Exception ex){
            logger.warning(ex.getMessage());
        }
        return  historyDtos;
    }

    public List<Book> getHistoryDetail(int historyId){
        List<Book> books = new ArrayList<>();
        try {
            List<HistoryDetail> historyDetails = historyDetailRepository.getByHistoryId(historyId);
            if(!historyDetails.isEmpty()){
                for(HistoryDetail historyDetail:historyDetails){
                    books.add(historyDetail.getBookId());
                }
            }
        }catch (Exception ex){
            logger.warning(ex.getMessage());
        }
        return books;
    }
    public List<HistoryRequestDto> getAllByRequest(){
        List<HistoryRequestDto> historyRequestDtos = new ArrayList<>();
        try {
            List<History> histories = historyRepository.getAllRequest();
            for(History history:histories){
                User user = userRepository.getById(history.getUser().getId());
                List<HistoryDetail> historyDetails = historyDetailRepository.getByHistoryId(history.getId());
                historyRequestDtos.add(HistoryMapper.mapHistory(history,historyDetails,user));
            }
        }catch (Exception ex){
            logger.warning(ex.getMessage());
        }
        return historyRequestDtos;
    }
    public List<History> getAll(){
        return  historyRepository.findAll();
    }

    public History findById(Integer id){
        return historyRepository.findById(id).orElse(null);
    }
}