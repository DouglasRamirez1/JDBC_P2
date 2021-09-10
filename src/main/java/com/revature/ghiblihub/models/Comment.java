package com.revature.ghiblihub.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Comments object model, consist of a content body and
 * the associated user and review
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    /**
     * Unique primary key with auto increment value for comments table
     */
    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    /**
     * The content body of this comment
     */
    @Column(name="content_body", columnDefinition = "TEXT")
    private String content;

    /**
     * The user that created this comment, foreign key referencing the User object
     */
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    /**
     * The review associated with this comment, foreign key referencing the Review object
     */
    @ManyToOne(targetEntity = Review.class)
    @JoinColumn(name="review_id",nullable = false)
    private Review review;
}
