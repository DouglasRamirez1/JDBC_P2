package com.revature.ghiblihub.service;

import com.revature.ghiblihub.models.Role;
import com.revature.ghiblihub.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RoleService defines methods to interact with database tables that
 * will be abstracted by SpringJPA and turned into JDBC at runtime
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * RoleService constructor, receives the roleRepository bean via Spring injection
     * at runtime.
     * @param roleRepository
     */
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves the Role object from the database based on its id, throws a RuntimeException
     * if it doesn't exist.
     * @param id
     * @return a Role object or a RuntimeException
     */
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
