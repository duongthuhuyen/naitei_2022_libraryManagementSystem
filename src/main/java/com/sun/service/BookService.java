package com.sun.service;

import java.util.List;

import com.sun.exception.BookException;
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
	public Book getBookById(int id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookException(BookException.BOOK_NOT_FOUND));
		return book;
	}

	public boolean save(Book book) {
		if (bookRepository.save(book) != null) {
			return true;
		}
		return false;
	}

	public Book getBook(Integer id) {
		return bookRepository.findById(id).orElse(null);
	}

	public Book editBook(int id, Book book) {
		Book book1 = bookRepository.findById(id).get();
		book1.setAuthor(book.getAuthor());
		book1.setName(book.getName());
		book1.setDescription(book.getDescription());
		book1.setPublisher(book.getPublisher());
		return bookRepository.save(book1);
	}
}	
