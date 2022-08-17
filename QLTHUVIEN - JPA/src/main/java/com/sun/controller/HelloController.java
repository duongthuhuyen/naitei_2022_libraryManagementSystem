package com.sun.controller;

import com.sun.entity.User;
import com.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {
    @Autowired
    UserService userService;
    @RequestMapping("/hello")
    public String greeting(ModelMap model){
        model.addAttribute("gret","hello");
        List<User> list = userService.getAll();
        return "greet";
    }
}
