package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.Category;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.CategoryRepo;
import com.example.bookrental.service.CategoryService;
import com.example.bookrental.utils.ExcelGenerator;
import com.example.bookrental.utils.ExcelToDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {
    private final ObjectMapper objectMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public String addCategory(CategoryDto categoryDto) {
        Category category = objectMapper.convertValue(categoryDto, Category.class);
        categoryRepo.save(category);
        return "category added"+categoryDto.getName();
    }

    @Override
    public String updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepo.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException("Category Not Found"));
        BeanUtils.copyProperties(categoryDto, category, getNullPropertyNames(categoryDto));
        categoryRepo.save(category);
        return "category added"+categoryDto.getName();
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()->new NotFoundException("Category does not exist "));
    }


    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Catagory Not Found"));
        categoryRepo.delete(category);
        return category.toString() + " has been deleted";
    }

    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response,categoryRepo.findAll(),"author sheet", Category.class);
        return "downloaded";
    }

    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Category> categories= ExcelToDb.createEntitiesFromExcel(file,Category.class);
        categoryRepo.saveAll(categories);
        return "excel sheet data added";
    }
}