package com.thegym.warehousemanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;

    @OneToMany(mappedBy = "cartonHeader",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sscc> ssccs = new ArrayList<>();

    @OneToMany(mappedBy = "cartonHeader", cascade = CascadeType.ALL)
    private Set<Item> items = new LinkedHashSet<>();

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