package com.craftorganizer.projectservice.service;

import com.craftorganizer.projectservice.mapper.ProjectMapper;
import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.model.Project;
import com.craftorganizer.projectservice.repository.PatternRepository;
import com.craftorganizer.projectservice.repository.ProjectRepository;
import com.craftorganizer.projectservice.service.dto.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private PatternRepository patternRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project testProject;
    private ProjectDto testProjectDto;
    private Pattern testPattern;

    final String PROJECT_ID = "1";
    final String PATTERN_ID = "2";
    final String USER_ID = "3";

    @BeforeEach
    void setUp() {


        testProject = new Project();
        testProject.setId(PROJECT_ID);
        testProject.setName("Test Project");
        testProject.setCreatedAt(new Date());
        testProject.setUpdatedAt(new Date());

        testProjectDto = new ProjectDto();
        testProjectDto.setId(PROJECT_ID);
        testProjectDto.setName("Test Project");

        testPattern = new Pattern();
        testPattern.setId(PATTERN_ID);
        testPattern.setPatternName("Test Pattern");
    }

    @Test
    void testCreateProject() {
        when(projectMapper.toEntity(any(ProjectDto.class))).thenReturn(testProject);
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);
        when(projectMapper.toDto(any(Project.class))).thenReturn(testProjectDto);

        ProjectDto createdProject = projectService.createProject(testProjectDto);

        assertNotNull(createdProject);
        assertEquals(PROJECT_ID, createdProject.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testGetProjectById_Found() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));
        when(projectMapper.toDto(testProject)).thenReturn(testProjectDto);

        ProjectDto foundProject = projectService.getProjectById(PROJECT_ID);

        assertNotNull(foundProject);
        assertEquals(PROJECT_ID, foundProject.getId());
        verify(projectRepository, times(1)).findById(PROJECT_ID);
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> projectService.getProjectById(PROJECT_ID));
        assertEquals("Project not found with ID: " + PROJECT_ID, exception.getMessage());
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(testProject));
        when(projectMapper.toDto(testProject)).thenReturn(testProjectDto);

        List<ProjectDto> projects = projectService.getAllProjects();

        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProject_Found() {
        ProjectDto updatedDto = new ProjectDto();
        updatedDto.setName("Updated Project");

        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));
        when(projectMapper.toDto(any(Project.class))).thenReturn(updatedDto);

        ProjectDto result = projectService.updateProject(PROJECT_ID, updatedDto);

        assertNotNull(result);
        assertEquals("Updated Project", result.getName());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProject_NotFound() {
        ProjectDto updatedDto = new ProjectDto();
        updatedDto.setName("Updated Project");

        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> projectService.updateProject(PROJECT_ID, updatedDto));
        assertEquals("Project not found with ID: " + PROJECT_ID, exception.getMessage());
    }

    @Test
    void testDeleteProject_Found() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));

        projectService.deleteProject(PROJECT_ID);

        verify(projectRepository, times(1)).delete(testProject);
    }

    @Test
    void testDeleteProject_NotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> projectService.deleteProject(PROJECT_ID));
        assertEquals("Project not found with ID: " + PROJECT_ID, exception.getMessage());
    }

    @Test
    void testAddPatternToProject_Success() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));
        when(patternRepository.findById(PATTERN_ID)).thenReturn(Optional.of(testPattern));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);
        when(projectMapper.toDto(any(Project.class))).thenReturn(testProjectDto);

        ProjectDto updatedProject = projectService.addPatternToProject(PROJECT_ID, PATTERN_ID);

        assertNotNull(updatedProject);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testAddPatternToProject_ProjectNotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> projectService.addPatternToProject(PROJECT_ID, PATTERN_ID));
        assertEquals("Project not found with ID: " + PROJECT_ID, exception.getMessage());
    }

    @Test
    void testAddPatternToProject_PatternNotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));
        when(patternRepository.findById(PATTERN_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> projectService.addPatternToProject(PROJECT_ID, PATTERN_ID));
        assertEquals("Pattern not found with ID: " + PATTERN_ID, exception.getMessage());
    }

    @Test
    void testAddPatternToProject_AlreadyExists() {
        testProject.setPatterns(new ArrayList<>(List.of(testPattern)));

        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(testProject));
        when(patternRepository.findById(PATTERN_ID)).thenReturn(Optional.of(testPattern));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> projectService.addPatternToProject(PROJECT_ID, PATTERN_ID));
        assertEquals("Pattern already added to the project.", exception.getMessage());
    }

    @Test
    void testGetProjectsByUserId() {
        when(projectRepository.findByUserId(USER_ID)).thenReturn(List.of(testProject));
        when(projectMapper.toDto(any(Project.class))).thenReturn(testProjectDto);

        List<ProjectDto> projects = projectService.getProjectsByUserId(USER_ID);

        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).findByUserId(USER_ID);
    }
}
