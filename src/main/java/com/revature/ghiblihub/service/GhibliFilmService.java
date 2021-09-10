package com.revature.ghiblihub.service;

import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.repository.GhibliFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GhibliFilmService defines methods to interact with database tables that
 * will be abstracted by SpringJPA and turned into JDBC at runtime
 */
@Service
public class GhibliFilmService {
    private final GhibliFilmRepository ghibliFilmRepository;

    /**
     * GhibliFilmService constructor, receives ghibliFilmRepository bean via
     * Spring injection at runtime.
     * @param ghibliFilmRepository
     */
    @Autowired
    public GhibliFilmService(GhibliFilmRepository ghibliFilmRepository){
        this.ghibliFilmRepository = ghibliFilmRepository;
    }

    /**
     * Retrieves a specific GhibliFilm object from the database based on its
     * id.
     * @param id
     * @return a GhibliFilm object
     */
    public GhibliFilm getFilmById(Integer id){
        if(ghibliFilmRepository.findById(id).isPresent()) {
            return ghibliFilmRepository.findById(id).get();
        } else {
            return null;
        }
    }

    /**
     * Retrieves a specific GhibliFilm object from the database based on its
     * title.
     * @param title
     * @return a GhibliFilm object
     */
    public GhibliFilm getFilmByTitle(String title){
        if (ghibliFilmRepository.findByTitle(title).isPresent()) {
            return ghibliFilmRepository.findByTitle(title).get();
        } else{
            return null;
        }
    }

    /**
     * Retrieves all GhibliFilm objects from the database and puts them in a List
     * @return a List of GhibliFilm objects
     */
    public List<GhibliFilm> getAllFilms(){
        return ghibliFilmRepository.findAll();
    }

    /**
     * Adds a new GhibliFilm object or updates an existing GhibliFilm object
     * in the database and then returns it.
     * @param ghibliFilm
     * @return a GhibliFilm object
     */
    public GhibliFilm saveFilm(GhibliFilm ghibliFilm){
        return ghibliFilmRepository.save(ghibliFilm);
    }

    /**
     * Locates a GhibliFilm object in the database by its id and removes it
     * if it exists.
     * @param id
     */
    public void deleteFilm(Integer id){
        ghibliFilmRepository.findById(id).ifPresent(ghibliFilmRepository::delete);
    }
}
