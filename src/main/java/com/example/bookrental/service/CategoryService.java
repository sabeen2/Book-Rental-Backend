package com.example.bookrental.service;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory(CategoryDto categoryDto);
    public Category updateCategory(CategoryDto categoryDto);
    public List<Category> getAllCategory();
    public Category findById(Long id);
    public String deleteCategory(Long id);
}
