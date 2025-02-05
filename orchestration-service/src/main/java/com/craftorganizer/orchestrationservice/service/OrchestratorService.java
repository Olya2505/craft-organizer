package com.craftorganizer.orchestrationservice.service;

import com.craftorganizer.orchestrationservice.service.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrchestratorService {

    private final RestTemplate restTemplate;

    private final String projectServiceBaseUrl = "http://localhost:8082";
    private final String userServiceBaseUrl = "http://localhost:8084";

    @Autowired
    public OrchestratorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProjectDto createProjectForUser(String userId, ProjectDto projectDto) {
        String createProjectUrl = projectServiceBaseUrl + "/projects";
        ResponseEntity<ProjectDto> projectResponse =
                restTemplate.postForEntity(createProjectUrl, projectDto, ProjectDto.class);

        if (!projectResponse.getStatusCode().is2xxSuccessful() || projectResponse.getBody() == null) {
            throw new RuntimeException("Failed to create project in Project service");
        }
        ProjectDto createdProject = projectResponse.getBody();

        String addProjectUrl = userServiceBaseUrl + "/users/{userId}/projects/{projectId}";
        restTemplate.postForEntity(addProjectUrl, null, Void.class, userId, createdProject.getId());

        return createdProject;
    }
}

