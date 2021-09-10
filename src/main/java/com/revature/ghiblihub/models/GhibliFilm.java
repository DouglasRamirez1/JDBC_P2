package com.revature.ghiblihub.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Ghibli Film object model, consist of various
 * information of Ghibli Studio films including: {
 *                                                  genre,
 *                                                  title,
 *                                                  director,
 *                                                  release year,
 *                                                  running time
 *                                               }
 */
@Entity
@Table(name = "ghibli_films")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GhibliFilm {

    /**
     * Unique primary key with auto increment value for ghibli_films table
     */
    @Id
    @Column(name="film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int filmId;

    /**
     * Genre of the film, foreign key referencing Genre object
     */
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genreId;

    /**
     * Title of the film in English
     */
    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    @JsonProperty("title")
    private String title;

    /**
     * The year of the film first released
     */
    @Column(name = "publication_year")
    @JsonProperty("release_date")
    private String release_date;

    /**
     * A short overview description of the film
     */
    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;

    /**
     * The director name of the film
     */
    @Column(name = "director", columnDefinition = "TEXT")
    @JsonProperty("director")
    private String director;

    /**
     * The length of the film in minutes
     */
    @Column(name = "runtime")
    @JsonProperty("running_time")
    private int running_time;

    @Override
    public String toString() {
        return "GhibliFilm{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", release_date=" + release_date +
                ", description='" + description + '\'' +
                ", director='" + director + '\'' +
                ", runtime=" + running_time + "min" +
                '}';
    }
}
