package com.revature.ghiblihub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AppController component of Spring MVC that will redirect to various pages in
 * the application.
 */
@Controller
public class AppController {

    /**
     * Takes in a request and redirects to the home html page
     * @return a String
     */
    @RequestMapping(value={"", "/", "/home"})
    public String homePage() {
        return "home";
    }

    /**
     * Takes in a request and redirects to the about html page
     * @return a String
     */
    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }
}
