package com.revature.ghiblihub.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Genre object model that specify the genre
 * of a film. Predefined in the database and static
 */
@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
public class Genre {

    /**
     * Unique primary key for the genres table
     */
    @Id
    @Column(name="genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    /**
     * The genre type
     */
    @Column(name = "genre_type")
    private String genreType;
}
