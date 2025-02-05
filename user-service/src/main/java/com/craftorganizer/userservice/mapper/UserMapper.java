package com.craftorganizer.userservice.mapper;

import com.craftorganizer.userservice.service.dto.UserDto;
import com.craftorganizer.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);

    void updateEntity(UserDto dto, @MappingTarget User user);
}
