package com.thegym.warehousemanagementsystem.entities;

import com.thegym.warehousemanagementsystem.enums.Action;
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
@Table(name = "stock_history")
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Size(max = 255)
    @NotNull
    @Column(name = "item_number", nullable = false)
    private String itemNumber;

    @NotNull
    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @Size(max = 255)
    @Column(name = "from_location_code")
    private String fromLocationCode;

    @Size(max = 255)
    @Column(name = "to_location_code")
    private String toLocationCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;

    @Size(max = 255)
    @Column(name = "sscc")
    private String sscc;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_timestamp", nullable = false)
    private Instant createdTimestamp;

    @PrePersist
    public void setCreatedTimestamp() {
        this.createdTimestamp = Instant.now();
    }


}