package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.UserEntityDto;
import com.example.bookrental.entity.UserEntity;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.UserEntityRepo;
import com.example.bookrental.service.UserEntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImplementation implements UserEntityService {
    private final UserEntityRepo userEntityRepo;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity addUser(UserEntityDto userEntityDto) {
        UserEntity userEntity;
        userEntity = objectMapper.convertValue(userEntityDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepo.save(userEntity);
    }

    @Override
    public String deactivateUser(Long id) {
        UserEntity user=userEntityRepo.findById(id).orElseThrow(()->new NotFoundException("User Not available"));
        userEntityRepo.delete(user);
        return user.toString()+" has been deleted";
    }
}
