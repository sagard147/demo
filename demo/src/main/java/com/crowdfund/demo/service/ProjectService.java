package com.crowdfund.demo.service;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.util.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.crowdfund.demo.repository.ProjectRepository;

import java.util.*;

@Service
public class ProjectService implements IProjectService{
    private final ProjectRepository projectRepository;
    private final UserAuthService userAuthService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          UserAuthService userAuthService){
        this.projectRepository = projectRepository;
        this.userAuthService = userAuthService;
    }

    @CircuitBreaker(name = "project_service", fallbackMethod = "getDefaultProjects")
    public List<Project> getProjects(Map<String, String> reqParam){
        String searchParam = reqParam.get("search");
        String userIdParam = reqParam.get("userId");
        Long userId = null;
        if (userIdParam != null && !userIdParam.isEmpty()) {
            if(!Helper.isValidStringId(userIdParam)){
                throw new ApiRequestException("Invalid userId Argument");
            }
            userId = Long.parseLong(userIdParam);
        }
        if (reqParam.get("testCircuit") != null && Boolean.parseBoolean(reqParam.get("testCircuit"))) {
            throw new IllegalMonitorStateException("Invalid userId Argument");
        }
        if (searchParam != null && !searchParam.isEmpty()) {
            return projectRepository.findByTitleContainingIgnoreCaseAndUserId(searchParam.toLowerCase(), userId);
        } else if (userId != null) {
            return projectRepository.findByTitleContainingIgnoreCaseAndUserId(null, userId);
        } else {
            return projectRepository.findAll();
        }
    }

    public Project getProjectById(long projectId){
        if (!Helper.isValidId(projectId)) {
            Logger.log("Invalid projectId Argument : "+projectId);
            throw new ApiRequestException("Invalid projectId Argument "+projectId);
        }
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isEmpty()){
            Logger.log("Invalid projectId Argument : "+projectId);
            throw new ApiRequestException("Project not found");
        }
        return projectOptional.orElse(null);
    }

    public Project addProject(ProjectDTO projectDTO){
        if(!Helper.validateAddProject(projectDTO)){
            Logger.log("Project with invalid data");
            throw new ApiRequestException("Project with invalid data");
        }
        Optional<Project> projectOptional = projectRepository.findProjectBytitle(projectDTO.getTitle().toLowerCase());
        if(projectOptional.isPresent()){
            Logger.log("Project with title already present  : "+projectDTO.getTitle());
            throw new ApiRequestException("Project with title already present");
        }
        User user = userAuthService.getUserById(projectDTO.getUserId());
        Project project = projectDTO.toProject();
        project.setUpdatedOn(new Date());
        project.setUser(user);
        projectRepository.save(project);
        return project;
    }

    public Project updateProject(long projectId, ProjectDTO projectDTO){
        if(!Helper.validateAddProject(projectDTO)){
            Logger.log("Project with invalid data");
            throw new ApiRequestException("Project with invalid data");
        }
        Project project = projectRepository.findById(projectDTO.getId()).orElse(null);
        if(project == null){
            Logger.log("Project is not found  : "+projectDTO.getTitle());
            throw new ApiRequestException("Project is not found");
        }
        if(project.getFundCollected() > 0L){
            Logger.log("Project is collected donation  : "+projectDTO.getTitle());
            throw new ApiRequestException("Project is collected donation");
        }
        if(!Objects.equals(projectDTO.getUserId(), project.getUser().getId())){
            Logger.log("User not allowed  : "+projectDTO.getTitle());
            throw new ApiRequestException("User not allowed");
        }
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setFundDemand(projectDTO.getFundDemand());
        project.setUpdatedOn(new Date());
        projectRepository.save(project);
        return project;
    }

    public void saveProject(Project project){
        projectRepository.save(project);
    }

    public List<Project> getDefaultProjects(Exception e) {
        return new ArrayList<>();
    }
}
