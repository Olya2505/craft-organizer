package com.craftorganizer.orchestrationservice.controller;

import com.craftorganizer.orchestrationservice.service.OrchestratorService;
import com.craftorganizer.orchestrationservice.service.dto.ProjectDto;
import com.craftorganizer.orchestrationservice.swagger.DescriptionVariables;
import com.craftorganizer.orchestrationservice.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orchestrator Controller", description = DescriptionVariables.ORCHESTRATOR)
@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {

    private final OrchestratorService orchestratorService;

    public OrchestratorController(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping("/users/{userId}/projects")
    @Operation(summary = "Create a project and add it to a user",
            description = "Creates a new project in the Project microservice and then associates it with the user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<ProjectDto> createProjectForUser(
            @PathVariable String userId,
            @RequestBody ProjectDto projectDto) {

        ProjectDto createdProject = orchestratorService.createProjectForUser(userId, projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }
}
