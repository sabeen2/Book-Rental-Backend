package com.example.bookrental.service;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Category;
import com.example.bookrental.projectioninterface.CategoryProjection;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    public String addCategory(CategoryDto categoryDto);
    public String updateCategory(CategoryDto categoryDto);
    public List<Category> getAllCategory();
    public List<CategoryProjection> findAllCategory();
    public Category findById(Long id);
    public String deleteCategory(Long id);
    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException;
    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException;
}
