package com.sun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.entity.Book;
import com.sun.repository.BookRepository;
@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	public List<Book> getAll(){
	       return  bookRepository.findAll();
	    }

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Book> searchBooks(String keyword) {
		if (keyword != null) {
			return bookRepository.search(keyword);
		}
		return bookRepository.findAll();
	}
	
	
}	
