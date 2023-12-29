package com.example.bookrental.Service;

import com.example.bookrental.Dto.CatagoryDto;
import com.example.bookrental.Entity.Catagory;

import java.util.List;

public interface CatagoryService {
    public Catagory addCatagory(CatagoryDto catagoryDto);
    public Catagory UpdateCatagory(CatagoryDto catagoryDto);
    public List<Catagory> getAllCatagory();
    public String deleteCatagory(long id);
}
