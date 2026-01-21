package com.thegym.warehousemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "\"row\"", nullable = false)
    private Integer row;

    @NotNull
    @Column(name = "section", nullable = false)
    private Integer section;

    @NotNull
    @Column(name = "shelf", nullable = false)
    private Integer shelf;

    @Size(max = 100)
    @NotNull
    @Column(name = "location_code", nullable = false, length = 100)
    private String locationCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonIgnoreProperties("locations")
    private Warehouse warehouse;

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

    @OneToMany(mappedBy = "location")
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