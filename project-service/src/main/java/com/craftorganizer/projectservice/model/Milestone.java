package com.craftorganizer.projectservice.model;

import java.util.Date;

public class Milestone {
    private String name;
    private Date deadline;
    private Boolean completed;

    public Milestone() {
    }

    public Milestone(String name, Date deadline, Boolean completed) {
        this.name = name;
        this.deadline = deadline;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
