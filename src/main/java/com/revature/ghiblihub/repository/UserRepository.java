package com.revature.ghiblihub.repository;

import com.revature.ghiblihub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to performing SQL queries for comments
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Custom method that gets an optional of User object by username
     * @param username username to search for
     * @return an optional of User object
     */
    Optional<User> findByUsername(String username);
}
