package com.sun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sun.entity.Type;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

}
