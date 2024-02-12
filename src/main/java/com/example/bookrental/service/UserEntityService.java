package com.example.bookrental.service;

import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.dto.responsedto.UserResponseDto;
import com.example.bookrental.entity.UserEntity;

import java.util.List;

public interface UserEntityService {
    public String addUser(UserEntityDto userEntityDto);
    public String deactivateUser(Long id);
    public String reactivateUser(Long id);
    List<UserResponseDto> getUsers();
}
