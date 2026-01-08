package com.thegym.warehousemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "item_number", nullable = false)
    private String itemNumber;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    @JsonIgnoreProperties("items")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carton_header_id", nullable = false)
    @JsonIgnoreProperties("items")
    private CartonHeader cartonHeader;

    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

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