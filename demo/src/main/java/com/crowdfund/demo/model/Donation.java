package com.crowdfund.demo.model;

import com.crowdfund.demo.mapper.DonationDTO;
import jakarta.persistence.*;

import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Donation")
@Table(name = "donation")
public class Donation {
    @Id
    @SequenceGenerator(
            name = "donation_sequence",
            sequenceName = "donation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "donation_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "fundAmount",
            nullable = false
    )
    private Long fundAmount;

    @Column(
            name = "currency",
            nullable = false,
            columnDefinition = "TEXT"
    )
    String currency;

    @Column(
            name = "transactionDate",
            nullable = false,
            columnDefinition = "DATE"
    )
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Donation() {
    }

    public Donation(Long fundAmount, String currency) {
        this.fundAmount = fundAmount;
        this.currency = currency;
        this.transactionDate = new Date();
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String  toString() {
        return "Donation{" +
                "id=" + id +
                ", currency ='" + currency + '\'' +
                ", fundDemand='" + fundAmount +
                '}';
    }

    public DonationDTO toDonationResponse(){
        DonationDTO donationDTO = new DonationDTO();
        donationDTO.setFundAmount(this.getFundAmount());
        donationDTO.setCurrency(this.getCurrency());
        donationDTO.setUserId(this.getUser().getId());
        donationDTO.setUserName(this.getUser().getFirstName() + " " + this.getUser().getLastName());
        donationDTO.setUserEmail(this.getUser().getEmail());
        donationDTO.setProjectId(this.getProject().getId());
        donationDTO.setProjectName(this.getProject().getTitle());
        donationDTO.setId(this.getId());
        donationDTO.setTransactionDate(this.getTransactionDate());
        return donationDTO;
    }
}
