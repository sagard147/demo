package com.crowdfund.demo.util;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class Helper {
    private static int TEXT_MIN_LEN = 3;
    private static int TEXT_MAX_LEN = 1000;
    private static long FUN_DEMAND_MIN = 10000L;
    private static long FUN_DEMAND_MAX = 100000L;
    private static long DONATION_MIN = 1L;
    private static HashSet<String> currencySet = new HashSet<>(Set.of("INR"));
    public static boolean isValidStringId(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidId(long id) {
        return id > 0L;
    }

    public static List<ProjectDTO> toProjectDTO(List<Project> projects) {
        try {
            return projects.stream().map(p->ProjectDTO.fromProject(p)).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public static ProjectDTO toProjectDTO(Project project) {
        try {
            return ProjectDTO.fromProject(project);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<DonationDTO> toDonationDTO(List<Donation> donations) {
        try {
            return donations.stream().map(d->d.toDonationResponse()).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public static DonationDTO toDonationDTO(Donation donation) {
        try {
            return donation.toDonationResponse();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateAddProject(ProjectDTO projectDTO){
        if(!isValidTextInput(projectDTO.getTitle())){
            throw new ApiRequestException("Invalid Project Title");
        }
        if(!isValidTextInput(projectDTO.getDescription())){
            throw new ApiRequestException("Invalid Project Description");
        }
        if(!currencySet.contains(projectDTO.getCurrency())){
            throw new ApiRequestException("Invalid Fund Currency");
        }
        if(!isValidFundDemandValue(projectDTO.getFundDemand())){
            throw new ApiRequestException("Fund Demand Value should be : "+FUN_DEMAND_MIN+" - "+FUN_DEMAND_MAX +" INR ");
        }
        if(!isValidId(projectDTO.getUserId())){
            throw new ApiRequestException("Invalid user id");
        }
        return true;
    }

    private static boolean isValidTextInput(String projectTitle) {
        return projectTitle != null && projectTitle.length() >= TEXT_MIN_LEN && projectTitle.length() <= TEXT_MAX_LEN;
    }

    private static boolean isValidFundDemandValue(long value) {
        return value >= FUN_DEMAND_MIN && value <= FUN_DEMAND_MAX;
    }

    private static boolean isValidDonationValue(long value) {
        return value >= DONATION_MIN;
    }

    public static boolean validateAddDonation(DonationDTO donationCreateDTO) {
        if(!isValidId(donationCreateDTO.getUserId())){
            throw new ApiRequestException("Invalid user id");
        }
        if(!isValidId(donationCreateDTO.getProjectId())){
            throw new ApiRequestException("Invalid project id");
        }
        if(!currencySet.contains(donationCreateDTO.getCurrency())){
            throw new ApiRequestException("Invalid Fund Currency");
        }
        if(!isValidDonationValue(donationCreateDTO.getFundAmount())){
            throw new ApiRequestException("Donation Amount Value should be  more than : "+DONATION_MIN);
        }
        return true;
    }
}
