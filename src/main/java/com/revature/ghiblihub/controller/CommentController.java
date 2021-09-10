package com.revature.ghiblihub.controller;

import com.revature.ghiblihub.WebUserDetails;
import com.revature.ghiblihub.models.Comment;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.CommentService;
import com.revature.ghiblihub.service.ReviewService;
import com.revature.ghiblihub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CommentController component of Spring MVC that will take in requests related to User
 * objects and resolve them utilizing dependency injections.
 */
@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ReviewService reviewService;

    /**
     * Constructor for CommentController, receives the commentService, reviewService, and userService
     * beans from Spring automatically at runtime.
     * @param commentService
     * @param reviewService
     * @param userService
     */
    @Autowired
    public CommentController(CommentService commentService, ReviewService reviewService, UserService userService){
        this.commentService = commentService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    /**
     * Takes in a request to retrieve all the Comment objects in the database from a specified Review
     * object based on its reviewId.
     * @param reviewId
     * @return a List of Comment objects
     */
    @GetMapping("/films/title/{title}/{reviewId}/comments")
    public @ResponseBody
    List<Comment> getAllCommentsFromReview(@PathVariable String reviewId){
        Review review = reviewService.getReviewByReviewId(Integer.parseInt(reviewId));
        return commentService.getAllCommentsByReview(review);
    }

    /**
     * Takes in a request to retrieve all the Comment objects in the database from a specified User
     * object based on its userId
     * @param userId
     * @return a List of Comment objects
     */
    @GetMapping("/userid/{userId}")
    public @ResponseBody
    List<Comment> getAllCommentsFromUser(@PathVariable String userId){
        User u = userService.getUserById(Integer.parseInt(userId));
        return commentService.getAllCommentsByUser(u);
    }

    /**
     * Takes in a request to retrieve all the Comment objects in the database from a specified User
     * object based on its username
     * @param username
     * @return a List of Comment objects
     */
    @GetMapping("/username/{username}")
    public @ResponseBody
    List<Comment> getAllCommentsFromUsername(@PathVariable String username){
        User u = userService.getUserByUsername(username);
        return commentService.getAllCommentsByUser(u);
    }

    /**
     * Takes a request to view all Comment objects in the database from a specified Review
     * object and redirects to the comments html page
     * @param title
     * @param reviewId
     * @return a String
     */
    @RequestMapping(value = "/films/title/{title}/{reviewId}", method = RequestMethod.GET)
    public String postReviewPage(@PathVariable String title, String reviewId) {
        return "comments";
    }

    /**
     * Takes in a request to create a new Comment object in the database using content
     * and reviewId parameters and redirects to the comments html page afterwards.
     * @param content
     * @param reviewId
     * @return a String
     */
    @RequestMapping(value = "/films/title/{title}/{reviewId}", method=RequestMethod.POST)
    public String createComment(@RequestParam String content, @PathVariable String reviewId){
        Comment comment = new Comment();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof WebUserDetails) {
            username = ((WebUserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User uId = userService.getUserByUsername(username);
        Review rId = reviewService.getReviewByReviewId(Integer.parseInt(reviewId));
        comment.setContent(content);
        comment.setUser(uId);
        comment.setReview(rId);
        commentService.saveComment(comment);
        return "comments";
    }

    /**
     * Takes in a Comment object as a request to update it in the database after checking if it
     * exists within the database.  If the Comment object cannot be found null is returned instead.
     * @param c
     * @return a Comment object or null
     */
    @PutMapping
    public @ResponseBody
    Comment updateComment(@RequestBody Comment c){
        if(commentService.getCommentByCommentId(c.getCommentId()).equals(null)){
            return null;
        }
        return commentService.saveComment(c);
    }

    /**
     * Takes in a request to delete a specific Comment object in the database based on its
     * commentId and returns an HTTPStatus OK code afterward.
     * @param id
     * @return HTTPStatus code 200
     */
    @DeleteMapping("/comments/{id}")
    ResponseEntity<HttpStatus> deleteComment(@PathVariable String id){
        commentService.deleteComment(Integer.parseInt(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
