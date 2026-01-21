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
@Table(name = "carton_headers")
public class CartonHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

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

    @OneToMany(mappedBy = "cartonHeader",fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cartonHeader")
    private Set<Item> items = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cartonHeader")
    @JsonIgnoreProperties("cartonHeader")
    private Set<Sscc> ssccs = new LinkedHashSet<>();

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