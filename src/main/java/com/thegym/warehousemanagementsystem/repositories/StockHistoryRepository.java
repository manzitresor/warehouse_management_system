package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    @Query("""
    SELECT s
    FROM StockHistory s
    WHERE s.warehouseNumber = :warehouseNumber
    AND s.itemNumber = :itemNumber
    AND (  :locationCode IS NULL
           OR s.fromLocationCode = :locationCode
           OR s.toLocationCode = :locationCode)
    """)
    List<StockHistory> findHistory(String warehouseNumber, String itemNumber, String locationCode);
}
