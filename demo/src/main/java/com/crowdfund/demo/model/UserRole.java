package com.crowdfund.demo.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "userRoles")
public class UserRole {

    @Id
    @SequenceGenerator(
            name = "userRole_sequence",
            sequenceName = "userRole_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "userRole_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles role;

    public UserRole(User user, Roles role) {
        this.user = user;
        this.role = role;
    }

    public UserRole() {
    }

    public User getUser() {
        return user;
    }

    public Roles getRole() {
        return role;
    }

    // Constructors, getters, and setters
}
