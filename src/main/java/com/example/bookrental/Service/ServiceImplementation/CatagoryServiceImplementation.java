package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.CatagoryDto;
import com.example.bookrental.Entity.Catagory;
import com.example.bookrental.Repo.CatagoryRepo;
import com.example.bookrental.Service.CatagoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

@AllArgsConstructor
@Service
public class CatagoryServiceImplementation implements CatagoryService {
    private final ObjectMapper  objectMapper;
    private final CatagoryRepo catagoryRepo;
    @Override
    public Catagory addCatagory(CatagoryDto catagoryDto) {
        Catagory catagory=objectMapper.convertValue(catagoryDto,Catagory.class);
        return catagoryRepo.save(catagory);
    }

    @Override
    public Catagory UpdateCatagory(CatagoryDto catagoryDto) {
        Catagory catagory=catagoryRepo.findById(catagoryDto.getId()).orElseThrow(()->new RuntimeException("catagory Not Found"));
        BeanUtils.copyProperties(catagoryDto, catagory, getNullPropertyNames(catagoryDto));
        return catagoryRepo.save(catagory);
    }

    @Override
    public List<Catagory> getAllCatagory() {
        return catagoryRepo.findAll();
    }

    @Override
    public String deleteCatagory(long id) {
        Catagory catagory=catagoryRepo.findById(id).orElseThrow(()->new RuntimeException("Catagory Not Found"));
        catagoryRepo.delete(catagory);
        return catagory.toString() +" has been deleted";
    }
}
