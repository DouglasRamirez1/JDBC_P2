package com.revature.ghiblihub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * User object model
 */
@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * Unique primary key with auto increment value for users table
     */
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * username of user, set to be non-null and unique
     */
    @Column(name="username", nullable = false, unique = true)
    private String username;

    /**
     * password of user, set to be non-null
     */
    @Column(name="password", nullable = false)
    private String password;

    /**
     * role of user, set to be non-null, a foreign key referencing the Role object
     */
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name="role", nullable = false)
    private Role role;
}
