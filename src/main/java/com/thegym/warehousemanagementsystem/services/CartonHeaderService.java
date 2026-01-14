package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.CartonHeaderRequestDto;
import com.thegym.warehousemanagementsystem.dtos.CartonHeaderResponseDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.CartonHeaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartonHeaderService {
    private CartonHeaderRepository cartonHeaderRepository;

    public CartonHeaderResponseDto create(CartonHeaderRequestDto cartonHeaderRequestDto) {
        if (cartonHeaderRepository.existsCartonHeadersByBarcode(cartonHeaderRequestDto.getBarcode())) {
            throw new ConflictException("Carton Header with '" + cartonHeaderRequestDto.getBarcode() + "' barcode already exists");
        }

        CartonHeader cartonHeader = new CartonHeader();
        cartonHeader.setBarcode(cartonHeaderRequestDto.getBarcode());
        cartonHeader.setDescription(cartonHeaderRequestDto.getDescription());
        cartonHeaderRepository.save(cartonHeader);

        return new CartonHeaderResponseDto(cartonHeader.getBarcode(),cartonHeader.getDescription());
    }

    public CartonHeaderResponseDto update(String barcode, CartonHeaderRequestDto cartonHeaderRequestDto) {
        CartonHeader cartonHeader = cartonHeaderRepository.findCartonHeaderByBarcode(barcode).orElse(null);
        if (cartonHeader == null) {
            throw new ResourceNotFoundException("Carton Header with barcode " + barcode + " not found");
        }

        cartonHeader.setDescription(cartonHeaderRequestDto.getDescription());
         cartonHeaderRepository.save(cartonHeader);
         return new CartonHeaderResponseDto(cartonHeader.getBarcode(),cartonHeader.getDescription());
    }
}
