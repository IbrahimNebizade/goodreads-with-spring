package com.company.goodreadsapp.mapper;

import com.company.goodreadsapp.dto.CreateUserCommand;
import com.company.goodreadsapp.dto.User;
import com.company.goodreadsapp.repository.jpa.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract User createUserCommandToUser(CreateUserCommand command);

    @Mapping(target = "id", ignore = true)
    public abstract UserEntity createUserCommandToUserEntity(CreateUserCommand command);
}
