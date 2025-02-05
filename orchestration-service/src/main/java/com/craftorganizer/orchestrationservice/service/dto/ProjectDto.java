package com.craftorganizer.orchestrationservice.service.dto;

import com.craftorganizer.projectservice.model.Milestone;
import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.model.ProjectStatus;
import com.craftorganizer.projectservice.model.ProjectType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;


public class ProjectDto {
    private String id;

    @NotBlank(message = "Project name is required")
    @Size(min = 5, message = "Project name must be at least 5 characters long")
    private String name;

    @NotNull(message = "Type is required")
    private ProjectType type;

    private ProjectStatus status;
    private String userId;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    private List<Milestone> milestone;
    private List<Pattern> patterns;

    public ProjectDto() {
    }

    public ProjectDto(String id, String name, ProjectType type, ProjectStatus status, String userId, String description,
                      Date createdAt, Date updatedAt, List<Milestone> milestone, List<Pattern> patterns) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.userId = userId;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.milestone = milestone;
        this.patterns = patterns;
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public List<Milestone> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<Milestone> milestone) {
        this.milestone = milestone;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public @NotBlank(message = "Type is required") ProjectType getType() {
        return type;
    }

    public void setType(@NotBlank(message = "Type is required") ProjectType type) {
        this.type = type;
    }

    public @NotBlank(message = "Project name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Project name is required") String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
