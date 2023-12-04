package com.crowdfund.demo.mapper;

public class DonationDTO {

    private Long id;
    private Long fundAmount;
    private String currency;
    private Long userId;
    private Long projectId;

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
}
