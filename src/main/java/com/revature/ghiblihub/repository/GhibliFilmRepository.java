package com.revature.ghiblihub.repository;

import com.revature.ghiblihub.models.GhibliFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to performing SQL queries for Ghibli Films
 */
@Repository
public interface GhibliFilmRepository extends JpaRepository<GhibliFilm, Integer> {
    /**
     * Custom method that find film by the title
     * @param title the title of the film
     * @return an optional of GhibliFilm object
     */
    Optional<GhibliFilm> findByTitle(String title);
}
