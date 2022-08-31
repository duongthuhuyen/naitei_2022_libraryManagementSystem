package com.sun.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.sun.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%" + " OR b.description LIKE %?1%" + " OR b.author LIKE %?1%")
    public List<Book> search(String keyword);

    Optional<Book> findById(int bookId);
}