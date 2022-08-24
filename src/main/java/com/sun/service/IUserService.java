package com.sun.service;

import com.sun.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    List<User> getUsers(String email);
    boolean addUser(User user);
}
