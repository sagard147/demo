package com.crowdfund.demo.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Project")
@Table(
        name = "project",
        uniqueConstraints = {
                @UniqueConstraint(name = "project_title_unique", columnNames = "title")
        }
)
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "project_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "fundDemand",
            nullable = false
    )
    private long fundDemand;

    @Column(
            name = "fundCollected",
            nullable = false
    )
    private long fundCollected;

    @Column(
            name = "currency",
            nullable = false,
            columnDefinition = "TEXT"
    )
    String currency;

    @Column(
            name = "createdOn",
            nullable = false,
            columnDefinition = "DATE"
    )
    @Temporal(TemporalType.DATE)
    private Date createdOn;

    @Column(
            name = "updatedOn",
            nullable = false,
            columnDefinition = "DATE"
    )
    @Temporal(TemporalType.DATE)
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(targetEntity = Donation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private List<Donation> donations;

    public Project(String title,
                   String description,
                   long fundDemand,
                   String currency) {
        this.title = title;
        this.description = description;
        this.fundDemand = fundDemand;
        this.fundCollected = 0;
        this.currency = currency;
        this.createdOn = new Date();
        this.updatedOn = new Date();
    }

    public Project() {

    }
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFundDemand() {
        return fundDemand;
    }

    public void setFundDemand(long fundDemand) {
        this.fundDemand = fundDemand;
    }

    public long getFundCollected() {
        return fundCollected;
    }

    public void setFundCollected(long fundCollected) {
        this.fundCollected = fundCollected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String  toString() {
        return "Student{" +
                "id=" + id +
                ", title ='" + title + '\'' +
                ", description ='" + description + '\'' +
                ", fundDemand='" + fundDemand +
                '}';
    }
}
