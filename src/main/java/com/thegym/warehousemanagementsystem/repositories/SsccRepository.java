package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Sscc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SsccRepository extends JpaRepository<Sscc, Long> {
    boolean existsBySscc(String sscc);
    Sscc findBySscc(String sscc);
}
