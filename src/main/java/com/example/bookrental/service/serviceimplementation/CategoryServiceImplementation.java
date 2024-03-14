package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Category;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.CategoryMapper;
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
import java.util.Optional;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {
    private final ObjectMapper objectMapper;
    private final CategoryRepo categoryRepo;
    private final CustomMessageSource messageSource;
    private final CategoryMapper categoryMapper;

    @Override
    public String addCategory(CategoryDto categoryDto) {

        Optional<Category> byName = categoryRepo.findByName(categoryDto.getName());
        if (byName.isPresent()) {
            Category existingCategory = byName.get();
            if (existingCategory.isDeleted()) {
                existingCategory.setDeleted(false);
                categoryRepo.save(existingCategory);
                return "member already existed so, active status is changed";
            }else {
                throw new NotFoundException(messageSource.get(ExceptionMessages.CONSTRAINT_VIOLATION.getCode()));
            }
        } else {
            Category category = objectMapper.convertValue(categoryDto, Category.class);
            categoryRepo.save(category);
            return messageSource.get(ExceptionMessages.SAVE.getCode())+" "+ category.getId();
        }

    }

    @Override
    public String updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepo.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        BeanUtils.copyProperties(categoryDto, category, getNullPropertyNames(categoryDto));
        categoryRepo.save(category);
        return messageSource.get(ExceptionMessages.UPDATE.getCode()) +" "+ category.getId();
    }

//    @Override
//    public List<Category> getAllCategory() {
//        return categoryRepo.findAll();
//    }

    public List<CategoryDto> getDeleted() {
        return categoryMapper.getDeleted();
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        return categoryMapper.getAllAuthors();
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryMapper.getById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
    }


    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        categoryRepo.delete(category);
        return category.getName() +" "+ messageSource.get(ExceptionMessages.DELETED.getCode());
    }

    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response, categoryRepo.findAll(), "author sheet", Category.class);
        return messageSource.get(ExceptionMessages.DOWNLOADED.getCode());
    }

    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Category> categories = ExcelToDb.createExcel(file, Category.class);
        categoryRepo.saveAll(categories);
        return messageSource.get(ExceptionMessages.EXPORT_EXCEL_SUCCESS.getCode());
    }
}