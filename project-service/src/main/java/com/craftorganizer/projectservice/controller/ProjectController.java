package com.craftorganizer.projectservice.controller;

import com.craftorganizer.projectservice.service.ProjectService;
import com.craftorganizer.projectservice.service.dto.ProjectDto;
import com.craftorganizer.projectservice.swagger.DescriptionVariables;
import com.craftorganizer.projectservice.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Controller", description = DescriptionVariables.PROJECT)
@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(
            summary = "Creates a new project",
            description = "Creates a new project entry with the provided details",
            responses = {
                    @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectDto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieves a project by ID",
            description = "Fetches a project using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectById(id));
    }

    @GetMapping
    @Operation(
            summary = "Retrieves all projects",
            description = "Fetches a list of all stored projects",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updates an existing project",
            description = "Updates the details of an existing project identified by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String id,
                                                    @Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(id, projectDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletes a project by ID",
            description = "Removes a project from the database using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/patterns/{patternId}")
    @Operation(
            summary = "Adds a pattern to a project",
            description = "Associates an existing pattern with the specified project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pattern added successfully"),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<ProjectDto> addPatternToProject(@PathVariable String projectId, @PathVariable String patternId) {
        ProjectDto updatedProject = projectService.addPatternToProject(projectId, patternId);
        return ResponseEntity.ok(updatedProject);
    }

//@GetMapping("/user/{userId}")
//    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable String userId) {
//        List<ProjectDto> projects = projectService.getProjectsByUserId(userId).toDto();
//        return ResponseEntity.ok(projects);
//    }

}
