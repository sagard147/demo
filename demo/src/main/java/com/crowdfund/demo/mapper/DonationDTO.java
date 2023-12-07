package com.crowdfund.demo.mapper;

import java.util.Date;

public class DonationDTO {

    private Long id;
    private Long fundAmount;
    private String currency;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long projectId;
    private String projectName;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    private Date transactionDate;

    public DonationDTO(Long fundAmount, String currency, Long userId, Long projectId) {
        this.fundAmount = fundAmount;
        this.currency = currency;
        this.userId = userId;
        this.projectId = projectId;
    }

    public DonationDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundAmount() {
        return fundAmount;
    }
    public void setFundAmount(Long fundAmount) {
        this.fundAmount = fundAmount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
