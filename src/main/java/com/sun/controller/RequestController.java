package com.sun.controller;


import com.sun.common.CurrentUser;
import com.sun.entity.Book;
import com.sun.service.BookService;
import com.sun.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String request(ModelMap model, HttpSession session, @PathVariable("id") String id) {
        String msg = "Success!";
        if (session.getAttribute("cart") == null) {
            List<Book> cart = new ArrayList<Book>();
            cart.add(bookService.getBookById(Integer.parseInt(id)));
            session.setAttribute("cart", cart);
        } else {
            List<Book> cart = (List<Book>) session.getAttribute("cart");
            int index = this.exists(Integer.parseInt(id), cart);
            if (index == -1) {
                cart.add(bookService.getBookById(Integer.parseInt(id)));
            } else {
                msg = "The book already exists in the cart";
            }
            session.setAttribute("cart", cart);
        }
        model.addAttribute("message", msg);
        System.out.println(msg);
        return "redirect:/home";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") String id, HttpSession session, ModelMap model) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        int index = this.exists(Integer.parseInt(id), cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        model.addAttribute("message", "Remove Success!");
        return "redirect:/request/view";
    }

    @RequestMapping(value = "/request/view")
    public String viewRequest(ModelMap modelMap, HttpSession session) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        modelMap.addAttribute("books", cart);
        return "/request/requestPage";
    }

    private int exists(int id, List<Book> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/request/delete/{id}", method = RequestMethod.GET)
    public String requestDelete(@PathVariable("id") String id, ModelMap model) {
        String msg = requestService.deleteRequest(Integer.parseInt(id));
        model.addAttribute("message", msg);
        return "redirect:/history";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.GET)
    public String requestAdd(ModelMap model, HttpSession session) {
        List<Book> books = (List<Book>) session.getAttribute("cart");
        String msg = "";
        if (books.isEmpty()) {
            msg = "Request Don't have any book";
        } else {
            msg = requestService.request(CurrentUser.currentUser.getId(), books);
            if (msg.equals("Send Success")) {
                session.removeAttribute("cart");
            }
        }
        model.addAttribute("message", msg);
        return "redirect:/home";
    }
}
