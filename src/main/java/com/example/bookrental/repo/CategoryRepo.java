package com.example.bookrental.repo;

import com.example.bookrental.entity.Category;
import com.example.bookrental.mapper.CategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query(value = "SELECT c.id,c.name,c.discription from tbl_catagory c",nativeQuery = true)
    List<CategoryMapper> getAllAuthors();

    @Query(value = "select * from tbl_catagory where deleted=true", nativeQuery = true)
    List<Category> getDeleted();

    @Query(value = "select * from tbl_catagory where name=?1",nativeQuery = true)
    Category getByName(String name);


    Optional<Category> findByName (String name);
}
