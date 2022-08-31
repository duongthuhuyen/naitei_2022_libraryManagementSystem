package com.sun.repository;

import com.sun.entity.HistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryDetailRepository extends JpaRepository<HistoryDetail, Integer> {
    @Query(value = "select * from historydetails where history_id = :historyId", nativeQuery = true)
    List<HistoryDetail> getByHistoryId(@Param("historyId") int historyId);

    @Modifying
    @Query(value = "Delete from historydetails where history_id = :historyId", nativeQuery = true)
    void deleteByHistoryId(@Param("historyId") int historyId);
}
