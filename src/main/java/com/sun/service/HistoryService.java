package com.sun.service;

import com.sun.entity.History;
import com.sun.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.transaction.Transactional;

@Service
@Transactional
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    public List<History> getAll(){
        return  historyRepository.findAll();
    }

    public History findById(Integer id){
        return historyRepository.findById(id).orElse(null);
    }
}