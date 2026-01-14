package com.thegym.warehousemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ssccs")
public class Sscc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sscc", nullable = false,unique = true)
    private String sscc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carton_header_id", nullable = false)
    @JsonIgnoreProperties("ssccs")
    private CartonHeader cartonHeader;

    @Column(name = "received_timestamp")
    private Instant receivedTimestamp;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;

    @PrePersist
    public void setDefaults() {
        this.createdTimestamp = Instant.now();
        this.updatedTimestamp = Instant.now();
    }

    @PreUpdate
    public void updateTimestamp() {
        this.updatedTimestamp = Instant.now();
    }

}