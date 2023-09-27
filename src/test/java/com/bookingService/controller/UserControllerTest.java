package com.bookingService.controller;

import com.bookingService.model.User;
import com.bookingService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {

        //create User Object
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Bob");
        user.setEmail("test@gmail.com");

        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user/register")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        verify(userService,times(1)).createUser(user);
    }

    @Test
    void updateUser() throws Exception {

        int userId=1;

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("Rahul");
        user.setEmail("test@gmail.com");

        when(userService.updateUser(user,userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/user/1")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Update Successfully"))
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        //verify
        verify(userService,times(1)).updateUser(user,userId);
    }

    @Test
    void getAllUser() throws Exception {
        //create list object
        List<User> userList= new ArrayList<>();

        //Create User Object
        User user1= new User();
        user1.setUserId(1);
        user1.setFirstName("Raj");
        user1.setEmail("test1@gmail.com");

        User user2= new User();
        user2.setUserId(2);
        user2.setFirstName("Rahul");
        user2.setEmail("test2@gmail.com");

        //add users in list
        userList.add(user1);
        userList.add(user2);

        //Mock behavior
        when(userService.getAllUser()).thenReturn(userList);

        //Perform Get request
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Raj"))
                .andExpect(jsonPath("$[1].userId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Rahul"))
                .andReturn();

        //Verify
        verify(userService,times(1)).getAllUser();
    }

    @Test
    void getUserById() throws Exception {
        //Create User Object
        int userId=1;
        User user= new User();
        user.setUserId(userId);
        user.setFirstName("Rahul");
        user.setEmail("test2@gmail.com");

        //Mock Behavior
        when(userService.getUserById(userId)).thenReturn(user);

        //Create Get Request
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/{userId}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully Fetched"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.firstName").value("Rahul"))
                .andReturn();

        //Verify
        verify(userService,times(1)).getUserById(userId);
    }

    @Test
    void deleteUser() throws Exception {
        //Create User Object
        int userId=1;
        User user= new User();
        user.setUserId(userId);
        user.setFirstName("Rahul");
        user.setEmail("test2@gmail.com");

        //Mock Behavior
        when(userService.deleteUser(userId)).thenReturn(user);

        //Create Delete request
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/user/delete/{userId}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User Deleted Successfully"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.firstName").value("Rahul"))
                .andReturn();

        //Verify
        verify(userService,times(1)).deleteUser(userId);
    }
}