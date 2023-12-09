package com.crowdfund.demo.controller;

import java.util.*;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.ProjectDTO;
import com.crowdfund.demo.model.Project;
import com.crowdfund.demo.service.ProjectService;
import com.crowdfund.demo.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class ProjectController {

    private final ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> listAllProject(@RequestParam Map<String, String> reqParam){
        List<Project> projects = projectService.getProjects(reqParam);
        List<ProjectDTO> projectsDTO = Helper.toProjectDTO(projects);
        return new ResponseEntity<List<ProjectDTO>>(projectsDTO, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable("projectId") long projectId){
        Project project = projectService.getProjectById(projectId);
        if(project == null){
            throw new ApiRequestException("No Projects Found");
        }
        ProjectDTO projectDTO = Helper.toProjectDTO(project);
        return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("/addProject")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO addProject){
        Project project = projectService.addProject(addProject);
        return new ResponseEntity<ProjectDTO>(Helper.toProjectDTO(project), HttpStatus.OK);
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("projectId") long projectId, @RequestBody ProjectDTO addProject){
        Project project = projectService.updateProject(projectId, addProject);
        return new ResponseEntity<ProjectDTO>(Helper.toProjectDTO(project), HttpStatus.OK);
    }

}
