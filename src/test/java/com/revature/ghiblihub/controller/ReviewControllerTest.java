package com.revature.ghiblihub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ghiblihub.models.GhibliFilm;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.GhibliFilmService;
import com.revature.ghiblihub.service.ReviewService;
import com.revature.ghiblihub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ReviewController reviewController;

    @MockBean
    private ReviewService reviewService;
    @MockBean
    private UserService userService;
    @MockBean
    private GhibliFilmService ghibliFilmService;

    private List<Review> reviewList;
    private Review review;
    private User user;
    private GhibliFilm film;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();

        user = new User();
        user.setUserId(1);
        user.setUsername("test_user1");
        user.setPassword("password");
        film = new GhibliFilm();
        film.setFilmId(1);
        film.setTitle("Title");
        film.setRelease_date("0123");
        film.setDescription("This is a description");
        film.setDirector("Director Name");
        review = new Review();
        review.setReviewId(1);
        review.setRating((float)2.2);
        review.setContent("This is a string");
        review.setUser(user);
        review.setFilm(film);

        reviewList = new ArrayList<>();
        reviewList.add(review);
    }

    @Test
    public void shouldReturnListOfReviewsWhenGetAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(reviewList);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].reviewId").value(1))
                .andExpect(jsonPath("$[0].rating").value((float)2.2))
                .andExpect(jsonPath("$[0].content").value("This is a string"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].film.filmId").value(1))
                .andExpect(jsonPath("$[0].film.title").value("Title"))
                .andExpect(jsonPath("$[0].film.release_date").value("0123"))
                .andExpect(jsonPath("$[0].film.description").value("This is a description"))
                .andExpect(jsonPath("$[0].film.director").value("Director Name"))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedReviewWhenGetById() throws Exception {
        when(reviewService.getReviewByReviewId(1)).thenReturn(review);

        mockMvc.perform(get("/films/title/Title/1/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.reviewId").value(1))
                .andExpect(jsonPath("$.rating").value((float)2.2))
                .andExpect(jsonPath("$.content").value("This is a string"))
                .andExpect(jsonPath("$.user.userId").value(1))
                .andExpect(jsonPath("$.user.username").value("test_user1"))
                .andExpect(jsonPath("$.user.password").value("password"))
                .andExpect(jsonPath("$.film.filmId").value(1))
                .andExpect(jsonPath("$.film.title").value("Title"))
                .andExpect(jsonPath("$.film.release_date").value("0123"))
                .andExpect(jsonPath("$.film.description").value("This is a description"))
                .andExpect(jsonPath("$.film.director").value("Director Name"))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedReviewsWhenGetByUserId() throws Exception {
        when(userService.getUserById(1)).thenReturn((user));
        when(reviewService.getReviewsByUser(user)).thenReturn(reviewList);

        mockMvc.perform(get("/reviews/userid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].reviewId").value(1))
                .andExpect(jsonPath("$[0].rating").value((float)2.2))
                .andExpect(jsonPath("$[0].content").value("This is a string"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].film.filmId").value(1))
                .andExpect(jsonPath("$[0].film.title").value("Title"))
                .andExpect(jsonPath("$[0].film.release_date").value("0123"))
                .andExpect(jsonPath("$[0].film.description").value("This is a description"))
                .andExpect(jsonPath("$[0].film.director").value("Director Name"))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedReviewsWhenGetByUsername() throws Exception {
        when(userService.getUserByUsername("test_user1")).thenReturn((user));
        when(reviewService.getReviewsByUser(user)).thenReturn(reviewList);

        mockMvc.perform(get("/reviews/username/test_user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].reviewId").value(1))
                .andExpect(jsonPath("$[0].rating").value((float)2.2))
                .andExpect(jsonPath("$[0].content").value("This is a string"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].film.filmId").value(1))
                .andExpect(jsonPath("$[0].film.title").value("Title"))
                .andExpect(jsonPath("$[0].film.release_date").value("0123"))
                .andExpect(jsonPath("$[0].film.description").value("This is a description"))
                .andExpect(jsonPath("$[0].film.director").value("Director Name"))
                .andReturn();
    }

//    @Test
//    public void shouldReturnNewReviewWhenReviewPost() throws Exception {
//        when(userService.getUserById(1)).thenReturn(user);
//        when(ghibliFilmService.getFilmById(1)).thenReturn(film);
//        when(reviewService.saveReview(review)).thenReturn(review);
//
//        mockMvc.perform(put("/reviews?rating=2.2&content=This is a string&userId=1&filmId=1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(review)))
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$.reviewId").value(1))
//                .andExpect(jsonPath("$.rating").value((float)2.2))
//                .andExpect(jsonPath("$.content").value("This is a string"))
//                .andExpect(jsonPath("$.user").value(user))
//                .andExpect(jsonPath("$.film").value(film))
//                .andReturn();
//    }

    @Test
    public void shouldReturnOKStatusWhenDeleteReview() throws Exception {
        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isOk());
    }
}