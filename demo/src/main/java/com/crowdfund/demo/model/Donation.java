package com.crowdfund.demo.model;

import com.crowdfund.demo.mapper.DonationDTO;
import jakarta.persistence.*;

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
        donationDTO.setProjectId(this.getProject().getId());
        return donationDTO;
    }
}
