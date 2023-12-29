package com.example.bookrental.Controller;

import com.example.bookrental.Dto.CatagoryDto;
import com.example.bookrental.Entity.Catagory;
import com.example.bookrental.Service.CatagoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catagory")
@AllArgsConstructor
public class CatagoryController {
    private  final CatagoryService catagoryService;

    @PostMapping("/addCatagory")
    public Catagory addCatagory(@RequestBody CatagoryDto catagoryDto){
        return catagoryService.addCatagory(catagoryDto);
    }
    @PutMapping("/updateCatagory")
    public Catagory UpdateCatagory(@RequestBody CatagoryDto catagoryDto){
        return catagoryService.UpdateCatagory(catagoryDto);
    }
    @GetMapping("/getallCatagoy")
    public List<Catagory> getAllCatagory(){
        return catagoryService.getAllCatagory();
    }
    @DeleteMapping("/deleteCatagory")
    public String deleteCatagory(@RequestParam  long id){
        return catagoryService.deleteCatagory(id);
    }
}
