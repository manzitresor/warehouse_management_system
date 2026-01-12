package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Sscc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SsccRepository extends JpaRepository<Sscc, Long> {
    boolean existsBySscc(String sscc);
    Optional<Sscc> findBySscc(String sscc);
}
