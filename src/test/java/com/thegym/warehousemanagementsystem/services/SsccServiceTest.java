package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.SsccRequestDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.CartonHeaderRepository;
import com.thegym.warehousemanagementsystem.repositories.SsccRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SsccServiceTest {
    @Mock
    SsccRepository ssccRepository;

    @Mock
    CartonHeaderRepository cartonHeaderRepository;

    @InjectMocks
    SsccService ssccService;

    private CartonHeader cartonHeader;
    private Sscc sscc;

    @BeforeEach
    public void setup() {
        cartonHeader = new CartonHeader();
        cartonHeader.setBarcode("40123451");
        cartonHeader.setDescription("Box with white t-shirts");
        cartonHeader.setId(1L);

        sscc = new Sscc();
        sscc.setSscc("003871234567890122");
        sscc.setCartonHeader(cartonHeader);
    }

    @Test
    @DisplayName("Should create Sscc successful")
    void sscc_create_successful() {
        SsccRequestDto ssccRequestDto = new SsccRequestDto("003871234567890122");
        when(cartonHeaderRepository.findById(1L)).thenReturn(Optional.of(cartonHeader));
        when(ssccRepository.save(any(Sscc.class))).thenReturn(sscc);

        Sscc sscc = ssccService.create(cartonHeader.getId(),ssccRequestDto);
        Assertions.assertNotNull(sscc);
    }

    @Test
    @DisplayName("Should throw if carton header not found")
    void sscc_create_return_notFound() {
        SsccRequestDto ssccRequestDto = new SsccRequestDto("003871234567890122");
        Long  cartonHeaderId = 5L;
        when(cartonHeaderRepository.findById(cartonHeaderId)).thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class,()-> ssccService.create(cartonHeaderId,ssccRequestDto));
    }

    @Test
    @DisplayName("Should verify Carton header lookup and association")
    void sscc_lookup_successful() {
        SsccRequestDto ssccRequestDto = new SsccRequestDto("003871234567890122");
        when(cartonHeaderRepository.findById(1L)).thenReturn(Optional.of(cartonHeader));
        when(ssccRepository.save(any(Sscc.class))).thenReturn(sscc);

        Sscc sscc = ssccService.create(cartonHeader.getId(),ssccRequestDto);
        verify(cartonHeaderRepository).findById(1L);


        ArgumentCaptor<Sscc> captor = ArgumentCaptor.forClass(Sscc.class);
        verify(ssccRepository).save(captor.capture());
        Sscc saved = captor.getValue();
        Assertions.assertEquals(cartonHeader, saved.getCartonHeader());
    }

}
