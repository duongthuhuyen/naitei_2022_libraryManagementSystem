package com.sun.controller;

import com.sun.service.IUserService;
import com.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping("/hello")
    public String greeting(ModelMap model){
        model.addAttribute("gret","hello");
        return "greet";
    }

}
