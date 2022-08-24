package com.sun.controller;

import com.sun.entity.User;
import com.sun.service.IUserService;
import com.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private IUserService iuserService;

    @GetMapping(path = "/register")
    public String register (Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/users/register";
    }


    @PostMapping(path = "/register")
    public String registerUser(Model model,@ModelAttribute("user") User user) {
        String errMsg = "";
        if (user.getPassword().equals(user.getMatchingPassword())) {
            user.setRole(User.ERole.USER);
            if (this.iuserService.addUser(user)) {
                return "redirect:/user/login";
            } else {
                errMsg = "Register Error";
            }
        } else {
            errMsg = "Password Matching error";
        }
        model.addAttribute("errMsg", errMsg);
        return "redirect:/user/register";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "users/login";
    }
}
