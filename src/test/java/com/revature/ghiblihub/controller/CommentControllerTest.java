package com.revature.ghiblihub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ghiblihub.models.Comment;
import com.revature.ghiblihub.models.Review;
import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.CommentService;
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
class CommentControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentService commentService;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private UserService userService;

    private List<Comment> commentList;
    private Comment comment;
    private Review review;
    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        user = new User();
        user.setUserId(1);
        user.setUsername("test_user1");
        user.setPassword("password");
        review = new Review();
        review.setReviewId(1);
        review.setRating((float)2.2);
        review.setContent("This is a string");
        comment = new Comment();
        comment.setCommentId(1);
        comment.setContent("This is a comment");
        comment.setUser(user);
        comment.setReview(review);

        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    public void shouldReturnAllCommentsFromSpecifiedReviewWhenGetByReviewId() throws Exception {
        when(reviewService.getReviewByReviewId(1)).thenReturn(review);
        when(commentService.getAllCommentsByReview(review)).thenReturn(commentList);

        mockMvc.perform(get("/films/title/Title/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].commentId").value(1))
                .andExpect(jsonPath("$[0].content").value("This is a comment"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].review.reviewId").value(1))
                .andExpect(jsonPath("$[0].review.rating").value((float)2.2))
                .andExpect(jsonPath("$[0].review.content").value("This is a string"))
                .andReturn();
    }

    @Test
    public void shouldReturnAllCommentsFromSpecifiedUserWhenGetByUserId() throws Exception {
        when(userService.getUserById(1)).thenReturn(user);
        when(commentService.getAllCommentsByUser(user)).thenReturn(commentList);

        mockMvc.perform(get("/userid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].commentId").value(1))
                .andExpect(jsonPath("$[0].content").value("This is a comment"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].review.reviewId").value(1))
                .andExpect(jsonPath("$[0].review.rating").value((float)2.2))
                .andExpect(jsonPath("$[0].review.content").value("This is a string"))
                .andReturn();
    }

    @Test
    public void shouldReturnAllCommentsFromSpecifiedUserWhenGetByUsername() throws Exception {
        when(userService.getUserByUsername("test_user1")).thenReturn(user);
        when(commentService.getAllCommentsByUser(user)).thenReturn(commentList);

        mockMvc.perform(get("/username/test_user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].commentId").value(1))
                .andExpect(jsonPath("$[0].content").value("This is a comment"))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].user.username").value("test_user1"))
                .andExpect(jsonPath("$[0].user.password").value("password"))
                .andExpect(jsonPath("$[0].review.reviewId").value(1))
                .andExpect(jsonPath("$[0].review.rating").value((float)2.2))
                .andExpect(jsonPath("$[0].review.content").value("This is a string"))
                .andReturn();
    }

//    @Test
//    public void shouldReturnNewCommentWhenUserPost() throws Exception {
//        when(commentService.getCommentByCommentId(comment.getCommentId())).thenReturn(comment);
//        when(commentService.saveComment(comment)).thenReturn(comment);
//
//        mockMvc.perform(put("/comments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(comment)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$.commentId").value(1))
//                .andExpect(jsonPath("$.content").value("This is a comment"))
//                .andExpect(jsonPath("$.user").value(user))
//                .andExpect(jsonPath("$.review").value(review))
//                .andReturn();
//    }

    @Test
    public void shouldReturnOKStatusWhenDeleteComment() throws Exception {
        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk());
    }

}