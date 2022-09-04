package com.sun.service;

import com.sun.entity.Type;
import com.sun.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }
}
