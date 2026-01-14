package com.thegym.warehousemanagementsystem.dtos.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SsccResponseDto {
    private Long id;
    private String sscc;
    private Instant receivedTimestamp;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;
}
