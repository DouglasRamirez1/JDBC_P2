package com.revature.ghiblihub.controller;

import com.revature.ghiblihub.WebUserDetails;
import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.GhibliFilmService;
import com.revature.ghiblihub.service.ReviewService;
import com.revature.ghiblihub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReviewController component of Spring MVC that will take in requests related to User
 * objects and resolve them utilizing dependency injections.
 */
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final GhibliFilmService ghibliFilmService;

    /**
     * Constructor for ReviewController, receives the reviewService, userService, and ghibliFilmService
     * beans from Spring at runtime.
     * @param reviewService
     * @param userService
     * @param ghibliFilmService
     */
    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, GhibliFilmService ghibliFilmService){
        this.reviewService = reviewService;
        this.userService = userService;
        this.ghibliFilmService = ghibliFilmService;
    }

    /**
     * Takes a request to retrieve all the Review objects in the database and returns them
     * in a List
     * @return a List of Review objects
     */
    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public @ResponseBody
    List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    /**
     * Takes a request to retrieve a specific Review object from the database by its reviewId.
     * @param reviewId
     * @return a Review object
     */
    @RequestMapping(value = "/films/title/{title}/{reviewId}/review", method = RequestMethod.GET)
    public @ResponseBody
    Review findReviewByReviewId(@PathVariable String reviewId){
        return reviewService.getReviewByReviewId(Integer.parseInt(reviewId));
    }

    /**
     * Takes a request to retrieve a specific Review object from the database by its
     * User object reference's userId.
     * @param userId
     * @return a Review object
     */
    @GetMapping("/reviews/userid/{userId}")
    public @ResponseBody
    List<Review> findReviewByUserId(@PathVariable String userId){
        User user = userService.getUserById(Integer.parseInt(userId));
        return reviewService.getReviewsByUser(user);
    }

    /**
     * Takes a request to retrieve a specific Review object from the database by its
     * User object reference's username.
     * @param username
     * @return
     */
    @GetMapping("/reviews/username/{username}")
    public @ResponseBody
    List<Review> findReviewByUsername(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        return reviewService.getReviewsByUser(user);
    }

    /**
     * Takes a request to retrieve all Review objects from the database that
     * are for a specific GhibliFilm object based on its title.
     * @param title
     * @return a List of Review objects
     */
    @GetMapping("/films/title/{title}/reviews")
    public @ResponseBody
    List<Review> findReviewByFilmTitle(@PathVariable String title) {
        GhibliFilm film = ghibliFilmService.getFilmByTitle(title);
        return reviewService.getReviewByFilm(film);
    }

    /**
     * Redirects the request to the postreview html page
     * @return a String
     */
    @RequestMapping("/postreview")
    public String postReviewPage() {
        return "postreview";
    }

    /**
     * Takes in a request to create a Review object based on rating, content, and filmTitle
     * parameters and add it to the database, afterwards redirecting the request to the
     * filmDetail html page.
     * @param rating
     * @param content
     * @param filmTitle
     * @return a String
     */
    @PutMapping
    @RequestMapping(value="/films/title/{filmTitle}", method = RequestMethod.POST)
    public String createReview(@RequestParam String rating, @RequestParam String content, @PathVariable String filmTitle){
        Review r = new Review();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof WebUserDetails) {
            username = ((WebUserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User u = userService.getUserByUsername(username);
        GhibliFilm f = ghibliFilmService.getFilmByTitle(filmTitle);
        r.setRating(Float.parseFloat(rating));
        r.setContent(content);
        r.setUser(u);
        r.setFilm(f);
        reviewService.saveReview(r);
        return "filmDetail";
    }

    /**
     * Takes in a request to delete a Review object from the database based on
     * its id, returning a HTTPStatus 200 code afterwards.
     * @param id
     * @return a HttpStatus.OK code response
     */
    @DeleteMapping("/reviews/{id}")
    ResponseEntity<HttpStatus> deleteReview(@PathVariable String id){
        reviewService.deleteReview(Integer.parseInt(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
