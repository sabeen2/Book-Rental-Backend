package com.example.bookrental.mapper;


import com.example.bookrental.dto.responsedto.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("Select u.id,u.username,u.user_type as userType,u.deleted from tbl_user u")
    List<UserResponseDto> getUsers();
}
