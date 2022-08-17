package com.sun.service;

import com.sun.entity.User;
import com.sun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public List<User> getAll(){
        List<User> list = new ArrayList<>();
        try{
             list = userRepository.findAll();
            System.out.println(list);
        }catch (Exception ex){

            System.out.println(ex.getMessage());
        }
        return list;
    }

}
