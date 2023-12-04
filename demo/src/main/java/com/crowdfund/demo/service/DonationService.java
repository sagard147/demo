package com.crowdfund.demo.service;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.repository.DonationRepository;
import com.crowdfund.demo.util.Helper;
import com.crowdfund.demo.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final ProjectService projectService;

    private final UserAuthService userAuthService;

    @Autowired
    public DonationService(DonationRepository donationRepository,
                           ProjectService projectService,
                           UserAuthService userAuthService){
        this.donationRepository = donationRepository;
        this.projectService = projectService;
        this.userAuthService = userAuthService;
    }

    public List<Donation> listAllDonation(){
        try{
            Logger.log("Donations Entered");
            return donationRepository.findAll();
        }
        catch (Exception ex){
            Logger.log("Exception in listAllDonation"+ex.getMessage());
            return new ArrayList<>();
        }
    }
    public List<Donation> getUserDonations(Long userId){
        Logger.log("getUserDonations Entered : userId - "+userId.toString());
        User user = userAuthService.getUserById(userId);
        if(user == null){
            Logger.log("Provided User not exist : userId - "+userId.toString());
            throw new ApiRequestException("Provided User not exist : "+userId.toString());
        }
        return donationRepository.findByUserId(userId);
    }

    public List<Donation> getProjectDonations(Long projectId){
        Logger.log("getProjectDonations Entered : projectId - "+projectId.toString());
        Project project = projectService.getProjectById(projectId);
        if(project == null){
            Logger.log("Provided Project not exist : projectId - "+projectId.toString());
            throw new ApiRequestException("Provided Project not exist - "+projectId.toString());
        }
        return donationRepository.findByProjectId(projectId);
    }

    public Donation addDonation(DonationDTO donationCreateDTO) {
        if(!Helper.validateAddDonation(donationCreateDTO)){
            Logger.log("Project with invalid data");
            throw new ApiRequestException("Project with invalid data");
        }
        User user = userAuthService.getUserById(donationCreateDTO.getUserId());
        Project project = projectService.getProjectById(donationCreateDTO.getProjectId());
        if(user == null){
            throw new ApiRequestException("Provided User not exist");
        }
        if(project == null){
            throw new ApiRequestException("Provided Project not exist");
        }
        Donation donation = new Donation();
        donation.setFundAmount(donationCreateDTO.getFundAmount());
        donation.setCurrency(donationCreateDTO.getCurrency());
        donation.setUser(user);
        donation.setProject(project);
        return donationRepository.save(donation);
    }
}
