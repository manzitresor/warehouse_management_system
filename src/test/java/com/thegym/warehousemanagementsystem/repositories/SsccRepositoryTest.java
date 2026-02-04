package com.thegym.warehousemanagementsystem.repositories;


import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SsccRepositoryTest {
    @Autowired
    private SsccRepository ssccRepository;

    @Autowired
    private CartonHeaderRepository cartonHeaderRepository;

    private Sscc sscc;
    private CartonHeader cartonHeader;

    @BeforeEach
    public void setup() {
        cartonHeader = new CartonHeader();
        cartonHeader.setBarcode("40123464");
        cartonHeader.setDescription("Box with white t-shirts");
        cartonHeaderRepository.saveAndFlush(cartonHeader);

        sscc = new Sscc();
        sscc.setSscc("003871234567890133");
        sscc.setCartonHeader(cartonHeader);
    }

    @Test
    @DisplayName("Should create Sscc successful")
    public void sscc_create_return_success(){
        ssccRepository.saveAndFlush(sscc);
        Assertions.assertNotNull(sscc);
    }

    @Test
    @DisplayName("Sscc should be unique")
    public void sscc_create_return_unique(){
        ssccRepository.saveAndFlush(sscc);
        Sscc duplicateSscc = new Sscc();
        duplicateSscc.setSscc("003871234567890133");
        duplicateSscc.setCartonHeader(cartonHeader);

        Assertions.assertThrows(DataIntegrityViolationException.class,()->ssccRepository.saveAndFlush(duplicateSscc));
    }


}
