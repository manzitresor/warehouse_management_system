package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.SsccRequestDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.CartonHeaderRepository;
import com.thegym.warehousemanagementsystem.repositories.SsccRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SsccService {
    private SsccRepository ssccRepository;
    private CartonHeaderRepository cartonHeaderRepository;


    public Sscc create(Long id, SsccRequestDto ssccRequestDto) {
        CartonHeader cartonHeader = cartonHeaderRepository.findById(id).orElse(null);
        if(cartonHeader == null ){
            throw new ResourceNotFoundException("Carton header not found");
        }

        if(ssccRepository.existsBySscc(ssccRequestDto.getSscc())){
            throw new ConflictException("Sscc already exists");
        }

        Sscc sscc = new Sscc();
        sscc.setSscc(ssccRequestDto.getSscc());
        sscc.setCartonHeader(cartonHeader);
        return ssccRepository.save(sscc);
    }
}
