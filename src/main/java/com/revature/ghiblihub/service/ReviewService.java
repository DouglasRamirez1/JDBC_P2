package com.revature.ghiblihub.service;


import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ReviewService defines methods to interact with database tables that
 * will be abstracted by SpringJPA and turned into JDBC at runtime
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * ReviewService constructor, receives the reviewRepository bean via Spring
     * injection at runtime.
     * @param reviewRepository
     */
    @Autowired
    public ReviewService (ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    /**
     * Adds or updates a Review object if it already exists in the database,
     * then returns it.
     * @param review
     * @return a Review object
     */
    public Review saveReview (Review review){
        return reviewRepository.save(review);
    }

    /**
     * Searches the database for a specific Review object based on its id
     * and returns it if it exists, otherwise throws an exception.
     * @param id
     * @return a Review object or a RuntimeException
     */
    public Review getReviewByReviewId (Integer id){
        return reviewRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /**
     * Retrieves all Review objects in the database that contain a specific User object reference
     * and puts them into a List
     * @param user
     * @return a List of Review objects
     */
    public List<Review> getReviewsByUser(User user){
        return reviewRepository.getReviewsByUser(user);
    }

    /**
     * Retrieves all Review objects in the database that contain a specific GhibliFilm object
     * reference and puts them into a List
     * @param film
     * @return a List of Review objects
     */
    public List<Review> getReviewByFilm(GhibliFilm film) {
        return reviewRepository.getReviewsByFilm(film);
    }

    /**
     * Retrieves all Review objects in the database and returns them in a List
     * @return a List of Review objects
     */
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    /**
     * Finds a specified Review object in the database by its reviewId and
     * deletes it if it exists.
     * @param reviewId
     */
    public void deleteReview (Integer reviewId){
        reviewRepository.findById(reviewId).ifPresent(reviewRepository::delete);
    }



}
