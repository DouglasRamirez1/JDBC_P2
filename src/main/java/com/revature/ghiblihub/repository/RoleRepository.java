package com.revature.ghiblihub.repository;

import com.revature.ghiblihub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to performing SQL queries for comments
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
