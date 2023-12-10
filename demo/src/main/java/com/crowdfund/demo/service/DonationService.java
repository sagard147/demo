package com.crowdfund.demo.service;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.repository.DonationRepository;
import com.crowdfund.demo.util.Helper;
import com.crowdfund.demo.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Thread.sleep;

@Service
@EnableCaching
public class DonationService implements IDonationService {
    private final DonationRepository donationRepository;
    private final IProjectService projectService;

    private final IUserAuthService userAuthService;

    @Autowired
    public DonationService(DonationRepository donationRepository,
                           IProjectService projectService,
                           IUserAuthService userAuthService){
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

    @Cacheable(value = "userDonationsCache", key = "#userId")
    public List<Donation> getUserDonations(Long userId){
        Logger.log("getUserDonations Entered : userId - "+userId.toString());
        User user = userAuthService.getUserById(userId);
        if(user == null){
            Logger.log("Provided User not exist : userId - "+userId.toString());
            throw new ApiRequestException("Provided User not exist : "+userId.toString());
        }
        return donationRepository.findByUserId(userId);
    }

    @Cacheable(value = "projectDonationsCache", key = "#projectId")
    public List<Donation> getProjectDonations(Long projectId){
        Logger.log("getProjectDonations Entered : projectId - "+projectId.toString());
        Project project = projectService.getProjectById(projectId);
        if(project == null){
            Logger.log("Provided Project not exist : projectId - "+projectId.toString());
            throw new ApiRequestException("Provided Project not exist - "+projectId.toString());
        }
        return donationRepository.findByProjectId(projectId);
    }

    @CacheEvict(value = {"userDonationsCache", "projectDonationsCache"}, allEntries = true)
    public Donation addDonation(DonationDTO donationCreateDTO) {
        if(!Helper.validateAddDonation(donationCreateDTO)){
            Logger.log("Project with invalid data");
            throw new ApiRequestException("Project with invalid data");
        }
        User user = userAuthService.getUserById(donationCreateDTO.getUserId());
        if(user == null){
            throw new ApiRequestException("Provided User not exist");
        }
        Donation donation = new Donation();
        donation.setFundAmount(donationCreateDTO.getFundAmount());
        donation.setCurrency(donationCreateDTO.getCurrency());
        donation.setUser(user);
        donation.setTransactionDate(new Date());
        saveDonation(donationCreateDTO.getProjectId(), donation);
        return donation;
    }

    private synchronized void saveDonation(long projectId, Donation donation){
        try{
            Logger.log("Entered saveDonation");
            Project project = projectService.getProjectById(projectId);
            if(project == null){
                throw new ApiRequestException("Provided Project not exist");
            }
            if(donation.getFundAmount()+project.getFundCollected() > project.getFundDemand()){
                throw new ApiRequestException("Donation Amount is more than remaining Fund required");
            }
            donation.setProject(project);
            donationRepository.save(donation);
            project.setFundCollected(project.getFundCollected()+donation.getFundAmount());
            projectService.saveProject(project);
            Logger.log("SaveDonation successful");
        }
        catch (ApiRequestException ex){
            Logger.log("Validation Failed in saveDonation : "+ex.getMessage());
            throw ex;
        }
        catch (Exception ex){
            Logger.log("Exception in saveDonation : "+ex.getMessage());
            throw new ApiRequestException("Something went wrong, try after sometime!");
        }
    }
}
