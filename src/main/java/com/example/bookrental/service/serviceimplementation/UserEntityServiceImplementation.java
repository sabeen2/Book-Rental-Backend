package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.dto.responsedto.UserResponseDto;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.UserMapper;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.service.UserEntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImplementation implements UserEntityService {
    private final UserEntityRepo userEntityRepo;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomMessageSource messageSource;
    private final UserMapper userMapper;

    @Override
    public String addUser(UserEntityDto userEntityDto) {
//        try {
            UserEntity userEntity;
            userEntity = objectMapper.convertValue(userEntityDto, UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntityRepo.save(userEntity);
            return messageSource.get(ExceptionMessages.SAVE.getCode()) + "User added-: " + userEntityDto.getUsername() + "\n Role-: " + userEntityDto.getUserType();
//        } catch (DataIntegrityViolationException ex) {
//            throw new NotFoundException(ex.getMessage());
//        }
    }

    @Override
    public String deactivateUser(Long id) {
        UserEntity user = userEntityRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not available"));
        userEntityRepo.delete(user);
        return user.getUsername() + " " + messageSource.get(ExceptionMessages.DELETED.getCode());
    }

    @Override
    public String reactivateUser(Long id) {
        UserEntity user = userEntityRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not available"));
        user.setDeleted(false);
        userEntityRepo.save(user);
        return user.getUsername() + " " + messageSource.get(ExceptionMessages.SUCCESS.getCode());
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return userMapper.getUsers();
    }
}
