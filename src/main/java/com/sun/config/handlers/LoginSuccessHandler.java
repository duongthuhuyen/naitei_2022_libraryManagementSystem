package com.sun.config.handlers;

import com.sun.entity.User;
import com.sun.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IUserService iUserService;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        FilterChain chain,
//                                        Authentication authentication) throws IOException, ServletException {
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication.getName());
        User user = this.iUserService.getUsers(authentication.getName()).get(0);
        System.out.println(user);
        request.getSession().setAttribute("currentUser", user);

        response.sendRedirect("/QLTHUVIEN_war/home");
    }
}
