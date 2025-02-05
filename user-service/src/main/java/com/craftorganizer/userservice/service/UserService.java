package com.craftorganizer.userservice.service;

import com.craftorganizer.userservice.service.dto.UserDto;
import com.craftorganizer.userservice.mapper.UserMapper;
import com.craftorganizer.userservice.model.User;
import com.craftorganizer.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        logger.info("User created with ID: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(String id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
        userMapper.updateEntity(userDto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        logger.info("User updated with ID: {}", updatedUser.getId());
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public UserDto addProjectToUser(String userId, String projectId) {
        logger.info("Adding project with ID: {} to user with ID: {}", projectId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        if (user.getProjectIds() == null) {
            user.setProjectIds(new ArrayList<>());
        }

        if (user.getProjectIds().contains(projectId)) {
            throw new IllegalArgumentException("Project already associated with the user.");
        }

        user.getProjectIds().add(projectId);
        User updatedUser = userRepository.save(user);
        logger.info("Project with ID: {} successfully added to user with ID: {}", projectId, userId);
        return userMapper.toDto(updatedUser);
    }
}
