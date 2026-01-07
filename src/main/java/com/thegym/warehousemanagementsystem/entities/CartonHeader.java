package com.thegym.warehousemanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "carton_headers")
public class CartonHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Column(name = "description")
    private String description;

    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;

    @PrePersist
    public void setDefaultValues() {
        this.createdTimestamp = Instant.now();
        this.updatedTimestamp = Instant.now();
    }
    @PreUpdate
    public void updateTimestamps() {
        this.updatedTimestamp = Instant.now();
    }
}