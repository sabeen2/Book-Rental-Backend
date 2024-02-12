package com.example.bookrental.mapper;

import com.example.bookrental.dto.AuthorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AuthorMapper {
    @Select("select a.author_id, a.email,a.mobile_number,a.name from tbl_author a where deleted=true")
    List<AuthorDto> getDeleted();

    @Select("select  a.author_id, a.email,a.mobile_number,a.name from tbl_author a where deleted =false")
    List<AuthorDto> getAllAuthors();

    @Select("select  a.author_id, a.email,a.mobile_number,a.name from tbl_author a where id=#{id} and deleted =false")
    Optional<AuthorDto> getById(@Param("id") Long id);
}
