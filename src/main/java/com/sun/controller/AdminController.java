package com.sun.controller;

import com.sun.dto.HistoryRequestDto;
import com.sun.entity.Book;
import com.sun.service.HistoryService;
import com.sun.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")

public class AdminController {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RequestService requestService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/request",method = RequestMethod.GET)
    public String Request(ModelMap model){
        List<HistoryRequestDto> historyRequestDtoList = historyService.getAllByRequest();
        model.addAttribute("requests",historyRequestDtoList);
        return "/request/admin/AcceptRequest";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/request/detail/{id}",method = RequestMethod.GET)
    public String RequestDetail(@PathVariable("id") String id, ModelMap model){
        List<Book> books = historyService.getHistoryDetail(Integer.parseInt(id));
        model.addAttribute("books",books);
        return "/request/admin/requestDetail";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/accept/{id}",method = RequestMethod.GET)
    public String acceptRequest(@PathVariable("id") String id,ModelMap model){
        String msg = requestService.acceptRequest(Integer.parseInt(id));
        model.addAttribute("message",msg);
        return "redirect:/admin/request";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/deny/{id}",method = RequestMethod.GET)
    public String denyRequest(@PathVariable("id") String id,ModelMap model){
        String msg = requestService.deleteRequest(Integer.parseInt(id));
        model.addAttribute("message",msg);
        return "redirect:/admin/request";
    }
}
