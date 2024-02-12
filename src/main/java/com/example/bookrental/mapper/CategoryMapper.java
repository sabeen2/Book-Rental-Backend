package com.example.bookrental.mapper;

import com.example.bookrental.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CategoryMapper {
     @Select("SELECT c.id,c.name,c.discription from tbl_catagory c")
     List<CategoryDto> getAllAuthors();

     @Select("SELECT c.id,c.name,c.discription from tbl_catagory c where id=#{id}")
     Optional<CategoryDto> getById(@Param("id") Long id);

     @Select(value = "select * from tbl_catagory where deleted=true")
     List<CategoryDto> getDeleted();
}