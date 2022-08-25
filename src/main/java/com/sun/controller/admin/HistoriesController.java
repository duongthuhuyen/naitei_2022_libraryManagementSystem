package com.sun.controller.admin;

import com.sun.entity.History;
import com.sun.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class HistoriesController {
    @Autowired
    private HistoryService historyService;
    @GetMapping("/histories")
    public String index(ModelMap model) {
        List<History> histories = historyService.getAll();
        model.addAttribute("histories", histories);
        return "admin/histories";
    }

    @GetMapping("/histories/{id}")
    public String show(@PathVariable Integer id, ModelMap model) {
        History history = historyService.findById(id);
        model.addAttribute("history", history);
        return "admin/history";
    }
}