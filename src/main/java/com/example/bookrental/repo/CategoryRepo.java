package com.example.bookrental.repo;

import com.example.bookrental.entity.Category;
import com.example.bookrental.projectioninterface.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query(value = "SELECT c.id,c.name,c.discription from tbl_catagory c",nativeQuery = true)
    List<CategoryProjection> getAllAuthors();
}
