package com.revature.ghiblihub.controller;

import com.revature.ghiblihub.models.Role;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.RoleService;
import com.revature.ghiblihub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController component of Spring MVC that will take in requests related to User
 * objects and resolve them utilizing dependency injections.
 */
@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * Constructor of UserController, receives the userService and roleService beans from
     * Spring automatically at runtime.
     *
     * @param userService
     * @param roleService
     */
    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Receives a request to get all the User objects in the database and returns all
     * User objects in a List.
     * @return a List of User objects
     */
    @GetMapping("/users")
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Receives a request to get a User object in the database by its ID and returns
     * the User object if it exists.
     * @param id
     * @return a User object
     */
    @GetMapping("/users/{id}")
    public @ResponseBody
    User findUserById(@PathVariable String id) {
        return userService.getUserById(Integer.parseInt(id));
    }

    /**
     * Receives a request to get a User object in the database by its username and returns
     * the User object if it exists.
     * @param username
     * @return a User object
     */
    @GetMapping("/users/username/{username}")
    public @ResponseBody
    User findUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Redirects the request to the login html page
     * @return a String
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Redirects the request to the newuser html page
     * @return a String
     */
    @RequestMapping(value = "/login/newuserPage", method = RequestMethod.GET)
    public String createUserPage() {
        return "newuser";
    }

    /**
     * Redirects the request to the profile html page
     * @return a String
     */
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    /**
     *Takes a request to take new username and password parameters into a User object and list
     * the User database, checks to see if the username is unique and redirects to the login page
     * if it is, redirects to the registerError page if it isn't.
     * @param username
     * @param password
     * @return a String
     */
    @RequestMapping(value = "/login/newuser", method = RequestMethod.POST)
    public String createUser(@RequestParam String username, @RequestParam String password){
        User u = new User();
        Role role = roleService.getRoleById(1);
        u.setUsername(username);
        u.setPassword(password);
        //u.setAccountType("User");
        u.setRole(role);
        try {
            userService.registerUser(u);
        } catch (Exception e) {
            return "registerError";
        }
        return "login";
    }

    /**
     * Takes a request to delete a User in the database by its specific id parameter. Returns
     * a HTTPStatus code 200 response if successful HTTPStatus code 404 response otherwise.
     * @param id
     * @return a HTTPStatus response
     */
    @DeleteMapping("/users/{id}")
    public @ResponseBody
    ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        if(userService.deleteUser(Integer.parseInt(id))) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

    /**
     * Takes in a User object as a request to update it in the database and returns the updated
     * version.
     * @param user
     * @return a User object
     */
    @PutMapping("/users")
    public @ResponseBody
    User updateUser(@RequestBody User user) {
        User u = userService.saveUser(user);
        return u;
    }
}
