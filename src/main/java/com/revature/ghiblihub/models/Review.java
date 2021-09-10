package com.revature.ghiblihub.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Review object model that contains user rating and
 * review content of a particular Ghibli Studio film
 * and the user that created this review
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    /**
     * Unique primary key with auto increment value for reviews table
     */
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    /**
     * The rating user gave from 1-5 to the film
     */
    @Column(name = "rating")
    private float rating;

    /**
     * The content of the review post
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * The user that created this review, foreign key referencing the User object
     */
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The target film of this review, foreign key referencing the Film object
     */
    @ManyToOne(targetEntity = GhibliFilm.class)
    @JoinColumn(name = "film_id", nullable = false)
    private GhibliFilm film;

}
