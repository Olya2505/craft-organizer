package com.craftorganizer.projectservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    private String name;
    private ProjectType type;
    private ProjectStatus status;
    private String userId;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    private List<Milestone> milestone;

    @DBRef
    private List<Pattern> patterns;

    public Project() {
    }

    public Project(String name, ProjectType type, ProjectStatus status, String userId, String description, Date createdAt,
                   Date updatedAt, List<Milestone> milestone, List<Pattern> patterns) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Milestone> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<Milestone> milestone) {
        this.milestone = milestone;
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }
}
