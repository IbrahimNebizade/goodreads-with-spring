package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.CreateUserCommand;
import com.company.goodreadsapp.dto.Page;
import com.company.goodreadsapp.dto.UserCreatedResponse;
import com.company.goodreadsapp.event.MailSenderEvent;
import com.company.goodreadsapp.mapper.LoginDetailMapper;
import com.company.goodreadsapp.mapper.UserMapper;
import com.company.goodreadsapp.model.Mail;
import com.company.goodreadsapp.repository.jpa.UserDetailsJpaRepository;
import com.company.goodreadsapp.repository.jpa.UserEntity;
import com.company.goodreadsapp.repository.jpa.UserJpaRepository;
import com.company.goodreadsapp.service.UserService;
import com.company.goodreadsapp.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userRepository;
    private final UserDetailsJpaRepository loginDetailRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public UserCreatedResponse createUser(CreateUserCommand command) {
        log.info("ActionLog.UserServiceImpl.createUser.start - command: {}", command);
        userRepository.findByAlias(command.getAlias())
                .ifPresent(user -> {
                    throw new RuntimeException("exception.alias.already-exist");
                });

        command.setPassword(PasswordUtil.hash(command.getPassword()));

        var user = UserMapper.INSTANCE.createUserCommandToUserEntity(command);
        user = userRepository.save(user);

        var loginDetail = LoginDetailMapper.INSTANCE
                .toUserDetailsEntity(command, user);

        loginDetailRepository.save(loginDetail);

        publisher.publishEvent(new MailSenderEvent(
                Mail.builder()
                        .to(loginDetail.getEmail())
                        .subject("Welcome " + user.getFirstname() + " " + user.getLastname())
                        .body("You're successfully registered!!")
                        .build()
        ));

        log.info("ActionLog.UserServiceImpl.createUser.end - command: {}", command);
        return new UserCreatedResponse(user.getId());
    }

    @Override
    public Page<List<UserEntity>> getAllUsers(Pageable pageable) {
        log.info("ActionLog.UserServiceImpl.getAllUsers.start - page: {}, size:{}", pageable.getPageNumber(), pageable.getPageSize());
        var userEntityPage = userRepository.findAll(pageable);
        log.info("ActionLog.UserServiceImpl.getAllUsers.end - page: {}, size:{}", pageable.getPageNumber(), pageable.getPageSize());
        return new Page<>(
                userEntityPage.getContent(),
                userEntityPage.getPageable().getPageNumber(),
                userEntityPage.getTotalPages(),
                userEntityPage.hasNext()
        );
    }
}
