package com.revature.ghiblihub.controller;


import com.revature.ghiblihub.models.User;
import com.revature.ghiblihub.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private List<User> userList;
    private User user;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setUserId(1);
        user.setUsername("test_user1");
        user.setPassword("password");

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    public void shouldReturnListOfUsersWhenGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].username").value("test_user1"))
                .andExpect(jsonPath("$[0].password").value("password"))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedUserWhenGetById() throws Exception {
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("test_user1"))
                .andExpect(jsonPath("$.password").value("password"))
                .andReturn();
    }

    @Test
    public void shouldReturnSpecifiedUserWhenGetByUsername() throws Exception {
        when(userService.getUserByUsername("test_user1")).thenReturn(user);

        mockMvc.perform(get("/users/username/test_user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("test_user1"))
                .andExpect(jsonPath("$.password").value("password"))
                .andReturn();
    }

//    @Test
//    public void shouldReturnNewUserWhenUserPut() throws Exception {
//        when(userService.saveUser(user)).thenReturn(user);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
//        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(user);
//
//        mockMvc.perform(put("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$.userId").value(1))
//                .andExpect(jsonPath("$.username").value("test_user1"))
//                .andExpect(jsonPath("$.password").value("password"))
//                .andReturn();
//    }


    @Test
    public void shouldReturnOKStatusWhenDeleteUser() throws Exception {
        when(userService.deleteUser(1)).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}