package com.thegym.warehousemanagementsystem.repositories;


import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.enums.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockHistoryRepositoryTest {
    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @BeforeEach
    public void setup() {
// Create a 'MOVE' history record: From LOC-A to LOC-B
        StockHistory history1 = new StockHistory();
        history1.setItemNumber("40123456");
        history1.setWarehouseNumber("W1");
        history1.setFromLocationCode("1-1-1");
        history1.setToLocationCode("1-1-2");
        history1.setQuantityChange(10);
        history1.setAction(Action.MOVE);
        stockHistoryRepository.saveAndFlush(history1);

        StockHistory history2 = new StockHistory();
        history2.setItemNumber("40123456");
        history2.setWarehouseNumber("W1");
        history2.setFromLocationCode("1-1-1");
        history2.setToLocationCode("1-1-3");
        history2.setQuantityChange(5);
        history2.setAction(Action.MOVE);
        stockHistoryRepository.saveAndFlush(history2);
    }

    @Test
    @DisplayName("Should get Histories successful")
    public void stockHistory_findHistory_Return_Success() {
        List<StockHistory> histories = stockHistoryRepository.findHistory("W1","40123456","1-1-1");
        Assertions.assertNotNull(histories);
    }

    @Test
    @DisplayName("Should find history when locationCode is null return all history with same warehouse and itemNumber")
    public void stockHistory_findHistory_nullLocation_returnsAllForItem() {
        List<StockHistory> histories = stockHistoryRepository.findHistory("W1","40123456",null);

        Assertions.assertEquals(2, histories.size());
        Assertions.assertEquals("40123456", histories.getFirst().getItemNumber());
    }

}
