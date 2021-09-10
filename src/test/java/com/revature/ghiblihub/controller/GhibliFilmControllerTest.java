package com.revature.ghiblihub.controller;

import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.service.GhibliFilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GhibliFilmControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private GhibliFilmController ghibliFilmController;

    @MockBean
    private GhibliFilmService ghibliFilmService;

    private List<GhibliFilm> filmList;
    private GhibliFilm film;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ghibliFilmController).build();

        film = new GhibliFilm();
        film.setFilmId(1);
        film.setTitle("Title");
        film.setRelease_date("2021");
        film.setDescription("This is a description");
        film.setDirector("Director Person");
        film.setRunning_time(100);

        filmList = new ArrayList<>();
        filmList.add(film);
    }

    @Test
    public void shouldReturnListOfFilmsWhenGetAllFilms() throws Exception {
        when(ghibliFilmService.getAllFilms()).thenReturn(filmList);

        mockMvc.perform(get("/films/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].filmId").value(1))
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("$[0].release_date").value("2021"))
                .andExpect(jsonPath("$[0].description").value("This is a description"))
                .andExpect(jsonPath("$[0].director").value("Director Person"))
                .andExpect(jsonPath("$[0].running_time").value(100))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedFilmWhenGetFilmById() throws Exception {
        when(ghibliFilmService.getFilmById(1)).thenReturn(film);

        mockMvc.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.filmId").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.release_date").value("2021"))
                .andExpect(jsonPath("$.description").value("This is a description"))
                .andExpect(jsonPath("$.director").value("Director Person"))
                .andExpect(jsonPath("$.running_time").value(100))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedFilmWhenGetFilmByTitle() throws Exception {
        when(ghibliFilmService.getFilmByTitle("Title")).thenReturn(film);

        mockMvc.perform(get("/films/title/Title/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.filmId").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.release_date").value("2021"))
                .andExpect(jsonPath("$.description").value("This is a description"))
                .andExpect(jsonPath("$.director").value("Director Person"))
                .andExpect(jsonPath("$.running_time").value(100))
                .andReturn();
    }




}