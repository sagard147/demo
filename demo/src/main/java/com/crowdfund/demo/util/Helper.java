package com.crowdfund.demo.util;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        if(isNull(projectDTO.getTitle()) || !isValidTextInput(projectDTO.getTitle())){
            throw new ApiRequestException("Invalid Project Title");
        }
        if(isNull(projectDTO.getDescription()) || !isValidTextInput(projectDTO.getDescription())){
            throw new ApiRequestException("Invalid Project Description");
        }
        if(isNull(projectDTO.getCurrency()) || !currencySet.contains(projectDTO.getCurrency())){
            throw new ApiRequestException("Invalid Fund Currency");
        }
        if(isNull(projectDTO.getFundDemand()) || !isValidFundDemandValue(projectDTO.getFundDemand())){
            throw new ApiRequestException("Fund Demand Value should be : "+FUN_DEMAND_MIN+" - "+FUN_DEMAND_MAX +" INR ");
        }
        if(isNull(projectDTO.getUserId()) || !isValidId(projectDTO.getUserId())){
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
        if(isNull(donationCreateDTO.getUserId()) || !isValidId(donationCreateDTO.getUserId())){
            throw new ApiRequestException("Invalid user id");
        }
        if(isNull(donationCreateDTO.getProjectId()) || !isValidId(donationCreateDTO.getProjectId())){
            throw new ApiRequestException("Invalid project id");
        }
        if(isNull(donationCreateDTO.getCurrency()) || !currencySet.contains(donationCreateDTO.getCurrency())){
            throw new ApiRequestException("Invalid Fund Currency");
        }
        if(isNull(donationCreateDTO.getFundAmount()) || !isValidDonationValue(donationCreateDTO.getFundAmount())){
            throw new ApiRequestException("Donation Amount Value should be  more than : "+DONATION_MIN);
        }
        return true;
    }

    public static boolean validateAddUser(UserDTO userDTO){
        if(isNull(userDTO.getFirstName()) || !isValidTextInput(userDTO.getFirstName())){
            throw new ApiRequestException("Invalid User first Name length");
        }
        if(isNull(userDTO.getLastName()) || !isValidTextInput(userDTO.getLastName())){
            throw new ApiRequestException("Invalid User last Name length");
        }
        if(isNull(userDTO.getBio()) || !isValidTextInput(userDTO.getBio())){
            throw new ApiRequestException("Invalid User bio");
        }
        if(isNull(userDTO.getAddress()) || !isValidTextInput(userDTO.getAddress())){
            throw new ApiRequestException("Invalid User address");
        }
        if(isNull(userDTO.getPassword()) || !isValidTextInput(userDTO.getPassword())){
            throw new ApiRequestException("Invalid user password length");
        }
        if(isNull(userDTO.getPassword()) || !isValidTextInput(userDTO.getPassword())){
            throw new ApiRequestException("Invalid user password length");
        }
        if(isNull(userDTO.getEmail()) || !isValidEmail(userDTO.getEmail())){
            throw new ApiRequestException("Invalid user email");
        }
        if(isNull(userDTO.getAccountType()) || !isValidAccountType(userDTO.getAccountType())){
            throw new ApiRequestException("Invalid user accountType");
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidAccountType(Role role) {
        try {
            return Arrays.asList(Role.values()).contains(role);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }
}
