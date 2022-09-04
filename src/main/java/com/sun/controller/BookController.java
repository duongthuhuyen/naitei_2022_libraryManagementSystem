package com.sun.controller;

import java.util.List;

import com.sun.entity.Type;
import com.sun.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(path = "/book/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		return "books/add";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/book/add")
	public String addBook(Model model, @ModelAttribute("book") Book book) {
		System.out.println(book);
		String msg = "";
		if (bookService.save(book)) {
			msg = "add book success";
			model.addAttribute("msg", msg);
			return "redirect:/home";
		} else {
			msg = "add book error";
			model.addAttribute("msg", msg);
			model.addAttribute("book", book);
			return "books/add";
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(path = "book/{id}/edit")
	public String editBook(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("book", bookService.getBook(id));
		return "books/edit";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "book/edit")
	public String editBook(@ModelAttribute("book") Book book) {
		System.out.println(book);
		bookService.editBook(book.getId(), book);
		return "redirect:/home";
	}
}	
