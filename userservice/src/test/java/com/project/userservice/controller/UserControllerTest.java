package com.project.userservice.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.userservice.constants.AppConstants;
import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;
import com.project.userservice.serviceImpl.UserServiceImpl;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO("Rahin", "rahin@test.com","password");
        User user = new User("Rahin", "rahin@test.com", "password");

        when(userServiceImpl.saveUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post(AppConstants.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rahin"))
                .andExpect(jsonPath("$.email").value("rahin@test.com"));
    }
    
    @Test
    void shouldGetUserById() throws Exception {
        User user = new User("Rahin", "rahin@test.com", "password");

        when(userServiceImpl.findById(1L)).thenReturn(user);

        mockMvc.perform(get(AppConstants.BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rahin"))
                .andExpect(jsonPath("$.email").value("rahin@test.com"));
    }
    
    @Test
    void shouldGetAllUsers() throws Exception {
        List<UserResponseDTO> users = List.of(
            new UserResponseDTO("Rahin", "rahin@test.com"),
            new UserResponseDTO("Ali", "ali@test.com")
        );

        when(userServiceImpl.findAll()).thenReturn(users);

        mockMvc.perform(get(AppConstants.BASE_URL + "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Rahin"));
    }

    @Test
    void shouldFailWhenInvalidRequest() throws Exception {
        UserDTO invalidDTO = new UserDTO("", "invalid-email", "");

        mockMvc.perform(post(AppConstants.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

}
