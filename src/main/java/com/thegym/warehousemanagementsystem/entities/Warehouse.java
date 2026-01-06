package com.thegym.warehousemanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "warehouse_number", nullable = false, length = 50)
    private String warehouseNumber;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("true")
    @Column(name = "active")
    private Boolean active;


    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;

    @PrePersist
    public void setDefaultValues() {
        this.active = true;
        this.createdTimestamp = Instant.now();
        this.updatedTimestamp = Instant.now();
    }

    @PreUpdate
    public void updateTimestamp() {
        this.updatedTimestamp = Instant.now();
    }


}