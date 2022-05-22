package com.company.goodreadsapp.mapper;

import com.company.goodreadsapp.dto.CreateUserCommand;
import com.company.goodreadsapp.dto.User;
import com.company.goodreadsapp.model.LoginDetail;
import com.company.goodreadsapp.repository.jpa.UserDetailsEntity;
import com.company.goodreadsapp.repository.jpa.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class LoginDetailMapper {
    public static final LoginDetailMapper INSTANCE = Mappers.getMapper(LoginDetailMapper.class);

    public abstract LoginDetail toLoginDetail(CreateUserCommand command, User user);

    public abstract UserDetailsEntity toUserDetailsEntity(CreateUserCommand command, UserEntity user);
}
