package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.CartonHeaderRequestDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.repositories.CartonHeaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartonHeaderService {
    private CartonHeaderRepository cartonHeaderRepository;

    public CartonHeader create(CartonHeaderRequestDto cartonHeaderRequestDto) {
        if(cartonHeaderRepository.existsCartonHeadersByBarcode(cartonHeaderRequestDto.getBarcode())) {
            throw new ConflictException("Carton Header with '"+cartonHeaderRequestDto.getBarcode()+"' barcode already exists");
        }

        CartonHeader cartonHeader = new CartonHeader();
        cartonHeader.setBarcode(cartonHeaderRequestDto.getBarcode());
        cartonHeader.setDescription(cartonHeaderRequestDto.getDescription());

       return cartonHeaderRepository.save(cartonHeader);
    }
}
