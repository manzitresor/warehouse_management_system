package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.HistoryParamDto;
import com.thegym.warehousemanagementsystem.entities.Action;
import com.thegym.warehousemanagementsystem.entities.StockHistory;
import com.thegym.warehousemanagementsystem.repositories.StockHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockHistoryServiceTest {

    @Mock
    private StockHistoryRepository stockHistoryRepository;

    @InjectMocks
    private StockHistoryService stockHistoryService;

    private StockHistory historyRecord;
    private HistoryParamDto historyParamDto;

    @BeforeEach
    void setup() {
        historyParamDto = new HistoryParamDto("W1", "40123456", "1-1-1");

        historyRecord = new StockHistory();
        historyRecord.setItemNumber("40123456");
        historyRecord.setWarehouseNumber("W1");
        historyRecord.setFromLocationCode("1-1-1");
        historyRecord.setToLocationCode("1-1-2");
        historyRecord.setQuantityChange(10);
        historyRecord.setAction(Action.MOVE);
    }

    @Test
    @DisplayName("Should return history records based on DTO parameters")
    public void testFindByWarehouseNumber() {
        when(stockHistoryRepository.findHistory(
                historyParamDto.getWarehouseNumber(),
                historyParamDto.getItemNumber(),
                historyParamDto.getLocationCode()))
                .thenReturn(List.of(historyRecord));

        List<StockHistory> result = stockHistoryService.getHistory(historyParamDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("40123456", result.getFirst().getItemNumber());
    }
}