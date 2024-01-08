package com.example.bookrental.service;

import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.entity.UserEntity;

public interface UserEntityService {
    public UserEntity addUser(UserEntityDto userEntityDto);
    public String deactivateUser(Long id);
}
