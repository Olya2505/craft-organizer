package com.craftorganizer.projectservice.service;

import com.craftorganizer.projectservice.mapper.ProjectMapper;
import com.craftorganizer.projectservice.model.Project;
import com.craftorganizer.projectservice.repository.ProjectRepository;
import com.craftorganizer.projectservice.service.dto.ProjectDto;
import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.repository.PatternRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final PatternRepository patternRepository;

    private static final Logger logger = LogManager.getLogger(ProjectService.class);


    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, PatternRepository patternRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.patternRepository = patternRepository;
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setCreatedAt(new java.util.Date());
        project.setUpdatedAt(new java.util.Date());

        Project savedProject = projectRepository.save(project);
        logger.info("Project created with ID: {}", savedProject.getId());
        return projectMapper.toDto(savedProject);
    }

    public ProjectDto getProjectById(String id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("Project not found with ID: {}", id);
                    return new NoSuchElementException("Project not found with ID: " + id);
                });
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        logger.info("Updating project with ID: {}", id);

        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + id));

        projectMapper.updateEntity(projectDto, existingProject);
        existingProject.setUpdatedAt(new java.util.Date());

        Project updatedProject = projectRepository.save(existingProject);
        logger.info("Project updated successfully with ID: {}", id);
        return projectMapper.toDto(updatedProject);
    }

    public void deleteProject(String id) {
        logger.info("Deleting project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + id));

        projectRepository.delete(project);
        logger.info("Project deleted successfully with ID: {}", id);
    }

    @Transactional
    public ProjectDto addPatternToProject(String projectId, String patternId) {
        logger.info("Adding pattern with ID: {} to project with ID: {}", patternId, projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + projectId));

        Pattern pattern = patternRepository.findById(patternId)
                .orElseThrow(() -> new NoSuchElementException("Pattern not found with ID: " + patternId));

        if (project.getPatterns() != null && project.getPatterns().contains(pattern)) {
            throw new IllegalArgumentException("Pattern already added to the project.");
        }

        if (project.getPatterns() == null) {
            project.setPatterns(new ArrayList<>());
        }

        project.getPatterns().add(pattern);
        project.setUpdatedAt(new Date());

        Project updatedProject = projectRepository.save(project);
        logger.info("Pattern with ID: {} successfully added to project with ID: {}", patternId, projectId);
        return projectMapper.toDto(updatedProject);
    }

    public List<ProjectDto> getProjectsByUserId(String userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }
}

