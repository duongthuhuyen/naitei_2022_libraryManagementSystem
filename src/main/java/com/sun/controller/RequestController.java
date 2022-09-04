package com.sun.controller;


import com.sun.common.CurrentUser;
import com.sun.entity.Book;
import com.sun.exception.BookException;
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
    public String request(ModelMap model, HttpSession session, @PathVariable("id") String bookId) {
        String msg = "Success!";
        if (session.getAttribute("cart") == null) {
            List<Integer> cart = new ArrayList<>();
            try {
                Book book = bookService.getBookById(Integer.parseInt(bookId));
                cart.add(Integer.parseInt(bookId));
                session.setAttribute("cart", cart);
            } catch (BookException ex) {
                msg = "Book Not Found";
            }
        } else {
            List<Integer> cart = (List<Integer>) session.getAttribute("cart");
            int index = this.exists(Integer.parseInt(bookId), cart);
            if (index == -1) {
                try {
                    Book book = bookService.getBookById(Integer.parseInt(bookId));
                    cart.add(Integer.parseInt(bookId));
                } catch (BookException ex) {
                    msg = "Book Not Found";
                }
            } else {
                msg = "The book already exists in the cart";
            }
            session.setAttribute("cart", cart);
        }
        model.addAttribute("message", msg);
        session.setAttribute("messageAddBook", msg);
        return "redirect:/home";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") String id, HttpSession session, ModelMap model) {
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");
        int index = this.exists(Integer.parseInt(id), cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        model.addAttribute("message", "Remove Success!");
        session.setAttribute("messageRemoveBook", "Remove Success");
        return "redirect:/request/view";
    }

    @RequestMapping(value = "/request/view")
    public String viewRequest(ModelMap modelMap, HttpSession session) {
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");
        List<Book> books = new ArrayList<>();
        if (cart != null) {
            for (Integer id : cart) {
                books.add(bookService.getBookById(id));
            }
            modelMap.addAttribute("books", books);
        } else {
            modelMap.addAttribute("books", null);
        }
        return "/request/requestPage";
    }

    private int exists(int id, List<Integer> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i) == id) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/request/delete/{id}", method = RequestMethod.GET)
    public String requestDelete(@PathVariable("id") String id, ModelMap model) {
        String msg = requestService.deleteRequest(Integer.parseInt(id));
        model.addAttribute("message", msg);
        return "redirect:/histories";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.GET)
    public String requestAdd(ModelMap model, HttpSession session) {
        List<Integer> books = (List<Integer>) session.getAttribute("cart");
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
        session.setAttribute("messageRequest", msg);
        return "redirect:/home";
    }
}
