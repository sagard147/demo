package com.crowdfund.demo;
import com.crowdfund.demo.controller.DonationController;
import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.service.DonationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DonationControllerTest {

    @InjectMocks
    private DonationController donationController;

    @Mock
    private DonationService donationService;

    @Test
    public void testListAllDonations() {
        // Mock data
        Roles innovatorRole = new Roles();
        innovatorRole.setRole(Role.INNOVATOR);
        Roles donorRole = new Roles();
        donorRole.setRole(Role.DONOR);
        Project project = new Project(
                "Adidas",
                "Des",
                (long)10000,"INR");
        User user1 = new User("Sam","hey1","a@a","bio1","address 1");
        UserRole userRole1 = new UserRole(user1, innovatorRole);
        project.setUser(user1);
        Donation donation1 = new Donation( 1000L, "INR");
        donation1.setProject(project);
        donation1.setUser(user1);
        Donation donation2 = new Donation( 2000L, "INR");
        donation2.setProject(project);
        donation2.setUser(user1);

        List<Donation> mockDonations = Arrays.asList(
                donation1,
                donation2
        );

        Mockito.when(donationService.listAllDonation()).thenReturn(mockDonations);

        ResponseEntity<List<DonationDTO>> response = donationController.ListAllDonation();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDonations.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testListUserDonations() {
        // Mock data
        Roles innovatorRole = new Roles();
        innovatorRole.setRole(Role.INNOVATOR);
        Roles donorRole = new Roles();
        donorRole.setRole(Role.DONOR);
        Project project = new Project("Adidas", "Des", (long)10000,"INR");
        User user1 = new User("Sam","hey1","a@a","bio1","address 1");
        project.setUser(user1);
        Donation donation1 = new Donation( 1000L, "INR");
        donation1.setProject(project);
        donation1.setUser(user1);
        Donation donation2 = new Donation( 2000L, "INR");
        donation2.setProject(project);
        donation2.setUser(user1);

        // Mock data
        long userId = 123; // Set the user ID for testing
        List<Donation> mockUserDonations = Arrays.asList(
                donation1,
                donation2
        );

        Mockito.when(donationService.getUserDonations(userId)).thenReturn(mockUserDonations);

        ResponseEntity<List<DonationDTO>> response = donationController.ListUserDonation(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDonations.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testListProjectDonations() {
        // Mock data
        Roles innovatorRole = new Roles();
        innovatorRole.setRole(Role.INNOVATOR);
        Roles donorRole = new Roles();
        donorRole.setRole(Role.DONOR);
        Project project = new Project("Adidas", "Des", (long)10000,"INR");
        User user1 = new User("Sam","hey1","a@a","bio1","address 1");
        project.setUser(user1);
        Donation donation1 = new Donation( 1000L, "INR");
        donation1.setProject(project);
        donation1.setUser(user1);
        Donation donation2 = new Donation( 2000L, "INR");
        donation2.setProject(project);
        donation2.setUser(user1);
        long projectId = 456; // Set the project ID for testing
        List<Donation> mockProjectDonations = Arrays.asList(
                donation1,
                donation2
        );

        Mockito.when(donationService.getProjectDonations(projectId)).thenReturn(mockProjectDonations);

        ResponseEntity<List<DonationDTO>> response = donationController.ListProjectDonation(projectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProjectDonations.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testAddDonation() {
        // Mock data
        Roles innovatorRole = new Roles();
        innovatorRole.setRole(Role.INNOVATOR);
        Roles donorRole = new Roles();
        donorRole.setRole(Role.DONOR);
        Project project = new Project("Adidas", "Des", (long)10000,"INR");
        User user1 = new User("Sam","hey1","a@a","bio1","address 1");
        project.setUser(user1);
        Donation donation1 = new Donation( 1000L, "INR");
        donation1.setProject(project);
        donation1.setUser(user1);
        Donation donation2 = new Donation( 2000L, "INR");
        donation2.setProject(project);
        donation2.setUser(user1);

        DonationDTO donationDTO = new DonationDTO();
        Donation createdDonation = donation1;

        Mockito.when(donationService.addDonation(donationDTO)).thenReturn(createdDonation);

        ResponseEntity<DonationDTO> response = donationController.addDonation(donationDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

//    @Test
//    public void testInvalidRequestAddDonation() {
//        // Mock data Long fundAmount, String currency, Long userId, Long projectId
//        DonationDTO invalidDonationDTO = new DonationDTO(100L,"INR",1L,6L);
//
//        ResponseEntity<DonationDTO> response = donationController.addDonation(invalidDonationDTO);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        // Add additional assertions based on your actual response structure for invalid requests
//    }
}

