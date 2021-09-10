package com.revature.ghiblihub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Role object model that specify the role of user logged in
 * Predefined in the database and static
 */
@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    /**
     * Unique primary key for role
     */
    @Id
    @Column(name="role_id")
    private int roleId;

    /**
     * The role of an account
     * "ADMIN" or "USER"
     */
    @Column(name="role", nullable = false)
    private String role;
}
