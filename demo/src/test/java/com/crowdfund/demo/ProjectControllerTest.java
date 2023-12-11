package com.crowdfund.demo;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatchers;
import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.ProjectDTO;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.controller.ProjectController;
import com.crowdfund.demo.service.ProjectService;
import com.crowdfund.demo.util.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void testListAllProject() {
        // Arrange
        Map<String, String> reqParam = new HashMap<>();
        List<Project> projects = Arrays.asList(new Project(), new Project());
        List<ProjectDTO> projectsDTO = Arrays.asList(new ProjectDTO(), new ProjectDTO());

        when(projectService.getProjects(reqParam)).thenReturn(projects);
        when(Helper.toProjectDTO(projects)).thenReturn(projectsDTO);

        // Act
        ResponseEntity<List<ProjectDTO>> responseEntity = projectController.listAllProject(reqParam);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projectsDTO, responseEntity.getBody());
    }

    @Test
    void testGetProjectNotFound() {
        long projectId = 1L;

        when(projectService.getProjectById(projectId)).thenReturn(null);

        assertThrows(ApiRequestException.class, () -> projectController.getProject(projectId));
    }

    @Test
    void testAddProjectWithException() {
        // Arrange
        ProjectDTO addProject = new ProjectDTO();

        when(projectService.addProject(addProject)).thenThrow(new RuntimeException("Some error occurred"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> projectController.addProject(addProject));
    }

    @Test
    void testUpdateProjectNotFound() {
        // Arrange
        long projectId = 1L;
        ProjectDTO updateProject = new ProjectDTO();

        when(projectService.updateProject(projectId, updateProject)).thenThrow(new ApiRequestException("Project Not Present"));

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> projectController.updateProject(projectId, updateProject));
    }

}

