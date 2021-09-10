package com.revature.ghiblihub.controller;

import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.service.GhibliFilmService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * GhibliFilmController component of Spring MVC that will take in requests related to User
 * objects and resolve them utilizing dependency injections.
 */
@Controller
public class GhibliFilmController {

    private static final String url = "https://ghibliapi.herokuapp.com/films";

    private final GhibliFilmService ghibliFilmService;

    private final RestTemplate restTemplate;

    /**
     * Constructor for GhibliFilmController, receives the ghibliFilmService and restTemplate beans
     * from Spring at runtime.
     * @param ghibliFilmService
     * @param restTemplate
     */
    @Autowired
    public GhibliFilmController(GhibliFilmService ghibliFilmService, RestTemplate restTemplate){
        this.ghibliFilmService = ghibliFilmService;
        this.restTemplate = restTemplate;
    }

    /**
     * Takes in a request to retrieve all GhibliFilm objects from the database and returns them in a List
     * @return a List of GhibliFilm objects.
     */
    @GetMapping("/films/api")
    public @ResponseBody
    List<GhibliFilm> getAllFilms(){
        return ghibliFilmService.getAllFilms();
    }

    /**
     * Redirects the request to the films html page.
     * @return a String
     */
    @GetMapping("/films")
    public String filmsPage(){
        return "films";
    }

    /**
     * Takes in a request to retrieve a specific GhibliFilm object from the database by its id and
     * returns it.
     * @param id
     * @return a GhibliFilm object
     */
    @GetMapping("/films/{id}")
    public @ResponseBody
    GhibliFilm findFilmById(@PathVariable String id){
        return ghibliFilmService.getFilmById(Integer.parseInt(id));
    }

    /**
     * Takes in a specific title of a GhibliFilm object and redirects the request
     * to the filmDetail html page
     * @param title
     * @return a String
     */
    @GetMapping("/films/title/{title}")
    String filmDetailPage(@PathVariable String title) {
        return "filmDetail";
    }

    /**
     * Takes a request for a specific GhibliFilm object in the database based on its title
     * and retrieves it.
     * @param title
     * @return a GhibliFilm object
     */
    @GetMapping("/films/title/{title}/detail")
    public @ResponseBody
    GhibliFilm getFilmByTitle(@PathVariable String title) {
        return ghibliFilmService.getFilmByTitle(title);
    }

//    /**
//     * Deprecated method to populate our database using scrubbed data from an external api
//     */
//    @RequestMapping(value="/populate", method=RequestMethod.POST)
//    public void postFilmInfo() {
//        GhibliFilm[] arr = restTemplate.getForObject(url, GhibliFilm[].class);
//        assert arr != null;
//        List<GhibliFilm> list = Arrays.asList(arr);
//        list.forEach(film -> System.out.println(film.toString()));
//        list.forEach(film -> ghibliFilmService.saveFilm(film));
//    }
}
