package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.CategoryRepo;
import com.example.bookrental.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {
    private final ObjectMapper objectMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public Category addCategory(CategoryDto categoryDto) {
        Category category = objectMapper.convertValue(categoryDto, Category.class);
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepo.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException("Category Not Found"));
        BeanUtils.copyProperties(categoryDto, category, getNullPropertyNames(categoryDto));
        return categoryRepo.save(category);
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
}