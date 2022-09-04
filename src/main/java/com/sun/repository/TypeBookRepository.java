package com.sun.repository;

import com.sun.entity.TypeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBookRepository extends JpaRepository<TypeBook, Integer> {
}
