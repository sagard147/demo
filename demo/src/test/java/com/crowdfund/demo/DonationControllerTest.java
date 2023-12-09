package com.crowdfund.demo;
import com.crowdfund.demo.controller.DonationController;
import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.service.DonationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Project project = new Project("Adidas", "Des", (long)10000,"INR");
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
}

