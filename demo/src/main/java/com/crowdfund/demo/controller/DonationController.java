package com.crowdfund.demo.controller;

import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.Donation;
import com.crowdfund.demo.service.DonationService;
import com.crowdfund.demo.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/donations")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class DonationController {
    private final DonationService donationService;
    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> ListAllDonation(){
        List<Donation> donations = donationService.listAllDonation();
        List<DonationDTO> donationsDTO = Helper.toDonationDTO(donations);
        return new ResponseEntity<List<DonationDTO>>(donationsDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DonationDTO>> ListUserDonation(@PathVariable("userId") long userId){
        List<Donation> donations = donationService.getUserDonations(userId);
        List<DonationDTO> donationsDTO = Helper.toDonationDTO(donations);
        return new ResponseEntity<List<DonationDTO>>(donationsDTO, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<DonationDTO>> ListProjectDonation(@PathVariable("projectId") long projectId){
        List<Donation> donations = donationService.getProjectDonations(projectId);
        List<DonationDTO> donationsDTO = Helper.toDonationDTO(donations);
        return new ResponseEntity<List<DonationDTO>>(donationsDTO, HttpStatus.OK);
    }

    @PostMapping("/addDonation")
    public ResponseEntity<DonationDTO> addDonation(@RequestBody DonationDTO donationCreateDTO) {
        DonationDTO createdDonation = donationService.addDonation(donationCreateDTO).toDonationResponse();
        return new ResponseEntity<DonationDTO>(createdDonation, HttpStatus.CREATED);
    }
}
