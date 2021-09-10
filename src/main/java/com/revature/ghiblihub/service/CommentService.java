package com.revature.ghiblihub.service;

import com.revature.ghiblihub.models.Comment;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentService defines methods to interact with database tables that
 * will be abstracted by SpringJPA and turned into JDBC at runtime
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * Constructor for CommentService, receives the commentRepository bean
     * via Spring injection at runtime.
     * @param commentRepository
     */
    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    /**
     * Adds a Comment object or updates it in the database if it already exists,
     * then returns it.
     * @param comment
     * @return a Comment object
     */
    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    /**
     * Retrieves a specific Comment object from the database based on its commentId.
     * @param commentId
     * @return a Comment object
     */
    public Comment getCommentByCommentId(Integer commentId){
        return commentRepository.findById(commentId).orElseGet(null);
    }

    /**
     * Retrieves all Comment objects from the database based on a reference
     * to a Review object and puts them in a List
     * @param review
     * @return a List of Comment objects
     */
    public List<Comment> getAllCommentsByReview(Review review){
        return commentRepository.getCommentByReview(review);
    }

    /**
     * Retrieves all Comment objects from the database based on a reference
     * to a User object and puts them in a List
     * @param user
     * @return a List of Comment objects
     */
    public List<Comment> getAllCommentsByUser(User user){
        return commentRepository.getCommentsByUser(user);
    }

    /**
     * Locates a Comment object in the database by its commentId and
     * removes it if it exists.
     * @param commentId
     */
    public void deleteComment(Integer commentId){
        commentRepository.findById(commentId).ifPresent(commentRepository::delete);
    }


}
