package com.sun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.entity.Book;
import com.sun.service.BookService;
@Controller
public class BookController {
	@Autowired
	private  BookService bookService;
	
	@RequestMapping("/home")
    public String showBook(ModelMap model){
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books/books";
	}
	
	@RequestMapping("/searchBook")
	public String searchBook(@Param("keyword") String keyword, Model model) {
		model.addAttribute("books", bookService.searchBooks(keyword));
		return "books/books";
	}
	
}	
