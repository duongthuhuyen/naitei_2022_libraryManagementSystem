package com.sun.controller;

import com.sun.common.CurrentUser;
import com.sun.dto.HistoryDto;
import com.sun.entity.Book;
import com.sun.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public String getHistory( ModelMap model){
        int id = 0;
        if(CurrentUser.currentUser != null) {
             id = CurrentUser.currentUser.getId();
        }
        List<HistoryDto> historyRequestDtos = historyService.getHistory(id);
        model.addAttribute("histories",historyRequestDtos);
        model.addAttribute("message","");
        return "/historyPage/history";
    }

    @RequestMapping(value = "/historydetail/{id}",method = RequestMethod.GET)
    public String getHistoryDetail(@PathVariable("id") String id,ModelMap model){
        List<Book> books = historyService.getHistoryDetail(Integer.parseInt(id));
        model.addAttribute("books",books);
        return "/historyPage/HistoryDetail";
    }
}
