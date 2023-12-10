package com.crowdfund.demo.service;

import com.crowdfund.demo.mapper.ProjectDTO;
import com.crowdfund.demo.model.Project;

import java.util.*;

public interface IProjectService {
    List<Project> getProjects(Map<String, String> reqParam);

    Project getProjectById(long projectId);

    Project addProject(ProjectDTO projectDTO);

    Project updateProject(long projectId, ProjectDTO projectDTO);

    void saveProject(Project project);

    List<Project> getDefaultProjects(Exception e);
}
