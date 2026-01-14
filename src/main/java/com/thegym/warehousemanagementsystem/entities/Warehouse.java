package com.thegym.warehousemanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @NotNull
    @Column(name = "warehouse_number", nullable = false, length = 50)
    private String warehouseNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;

    @OneToMany(mappedBy = "warehouse",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations = new ArrayList<>();

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