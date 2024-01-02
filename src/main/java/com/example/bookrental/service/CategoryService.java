package com.example.bookrental.service;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory(CategoryDto categoryDto);
    public Category updateCategory(CategoryDto categoryDto);
    public List<Category> getAllCategory();
    public String deleteCategory(Long id);
}
