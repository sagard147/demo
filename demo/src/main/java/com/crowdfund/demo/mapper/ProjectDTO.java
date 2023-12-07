package com.crowdfund.demo.mapper;

import com.crowdfund.demo.model.Project;

import java.util.Date;

public class ProjectDTO {

    private Long id;
    private String title;
    private String description;
    private long fundDemand;
    private long fundCollected;
    private String currency;
    private Long userId;
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFundDemand() {
        return fundDemand;
    }

    public void setFundDemand(long fundDemand) {
        this.fundDemand = fundDemand;
    }

    public long getFundCollected() {
        return fundCollected;
    }

    public void setFundCollected(long fundCollected) {
        this.fundCollected = fundCollected;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public static ProjectDTO fromProject(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setTitle(project.getTitle());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setFundDemand(project.getFundDemand());
        projectDTO.setFundCollected(project.getFundCollected());
        projectDTO.setCurrency(project.getCurrency());
        projectDTO.setUserId(project.getUser() != null ? project.getUser().getId() : null);
        projectDTO.setCreatedOn(project.getCreatedOn());
        return projectDTO;
    }

    public Project toProject() {
        Project project = new Project(
                this.getTitle(),
                this.getDescription(),
                this.getFundDemand(),
                this.getCurrency()
        );

        project.setFundCollected(this.getFundCollected());
        return project;
    }

    // ... getters and setters

    // Optional: You may want to override toString() for logging or debugging
    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", fundDemand=" + fundDemand +
                ", fundCollected=" + fundCollected +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                '}';
    }
}
