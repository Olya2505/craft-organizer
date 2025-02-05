package com.craftorganizer.projectservice.mapper;

import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.service.dto.PatternDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatternMapper {
    Pattern toEntity(PatternDto dto);
    PatternDto toDto(Pattern entity);

    @Mapping(target = "id", source = "id")
    void updateEntity(PatternDto dto, @MappingTarget Pattern pattern);
}
