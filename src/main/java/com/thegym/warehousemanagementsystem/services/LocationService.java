package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.requestDto.LocationRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.LocationResponseDto;
import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService {
    private LocationRepository locationRepository;
    private WarehouseRepository warehouseRepository;

    @Transactional
    public LocationResponseDto create(Long warehouseId, LocationRequestDto locationRequestDto) {
        var warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if(warehouse == null){
            throw new ResourceNotFoundException("Warehouse not found");
        }

        var locationCode = locationRequestDto.getRow()+"-"+locationRequestDto.getSection()+"-"+locationRequestDto.getShelf();
        if(locationRepository.existsByWarehouseIdAndLocationCode(warehouseId,locationCode)){
            throw new ConflictException("Location with code '" + locationCode + "' already exists in this warehouse");
        }

        var location = new Location();
        location.setWarehouse(warehouse);
        location.setRow(locationRequestDto.getRow());
        location.setSection(locationRequestDto.getSection());
        location.setShelf(locationRequestDto.getShelf());
        location.setLocationCode(locationCode);

        locationRepository.save(location);


        return new LocationResponseDto(
                location.getRow(),
                location.getSection(),
                location.getSection(),
                location.getLocationCode()
        );
    }

}
