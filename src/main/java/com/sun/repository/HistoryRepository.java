package com.sun.repository;

import com.sun.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query(value = "select * from histories where user_id = :userId", nativeQuery = true)
    List<History> findByUserId(@Param("userId") int userId);

    @Modifying
    @Query(value = "Delete from histories where id = :id", nativeQuery = true)
    void deleteById(@Param("id") int id);

    Optional<History> findById(int id);

    @Query(value = "select * from histories where status = 'REQUEST'", nativeQuery = true)
    List<History> getAllRequest();

    @Modifying
    @Query(value = "update histories set status = 'ACCEPT',borrow_date =:borrowDate where id = :id", nativeQuery = true)
    void acceptRequest(@Param("id") int id, @Param("borrowDate") LocalDateTime borrowDate);
}