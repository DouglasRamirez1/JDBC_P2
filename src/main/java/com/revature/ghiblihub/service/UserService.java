package com.revature.ghiblihub.service;

import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService defines methods to interact with database tables that
 * will be abstracted by SpringJPA and turned into JDBC at runtime
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * UserService constructor, receives the userRepository bean at runtime from
     * Spring injection
     * @param userRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a User object or updates an existing User object if it already
     * exists in the database then returns the User object that was added/ returned.
     * @param user
     * @return A User object
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Searches the database for a specific User object based on its id
     * and returns it if it exists, otherwise throws an exception.
     * @param id
     * @return a User object or a RuntimeException
     */
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /**
     * Searches the database for a specific User object based on its username
     * and returns it if it exists, otherwise throws an exception.
     * @param username
     * @return a User object or a RuntimeException
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    }

    /**
     * Searches the database for a User object and if it doesn't exist adds
     * it to the database and returns it, throws an Exception instead if
     * said User object already exists in the database.
     * @param user
     * @return a User object
     * @throws Exception
     */
    public User registerUser(User user) throws Exception{
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new Exception();
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves all User objects in the database and puts them into a List.
     * @return a List of User objects
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Checks the database for a User object based on its id and
     * removes it if it exists and then returns a boolean based on
     * the object's existence and removal.
     * @param id
     * @return true if deleted User, false if User not found
     */
    public boolean deleteUser(Integer id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.getById(id));
            return true;
        } else {
            return false;
        }
    }
}
