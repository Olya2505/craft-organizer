package com.craftorganizer.projectservice.mapper;

import com.craftorganizer.projectservice.model.Project;
import com.craftorganizer.projectservice.service.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto dto);

    ProjectDto toDto(Project entity);

    void updateEntity(ProjectDto dto, @MappingTarget Project project);
}
