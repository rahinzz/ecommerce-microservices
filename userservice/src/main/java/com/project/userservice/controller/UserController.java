package com.project.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;
import com.project.userservice.serviceImpl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "APIs for managing users")
public class UserController {
	
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}
	
	@PostMapping("/")
	@Operation(summary = "Create a new user")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){	
		if (userDTO == null) 
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter the User details."); 
		
		try {
			User savedUser = userServiceImpl.saveUser(userDTO);
			UserResponseDTO userResponse = new UserResponseDTO(savedUser.getName(), savedUser.getEmail());
			return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving user.");
		}
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
	    try {
	        User user = userServiceImpl.findById(id);
	        if(user == null)
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	        
	        UserResponseDTO userResponse = new UserResponseDTO(user.getName(), user.getEmail());
	        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	        
	    } catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	    }
	}

	@GetMapping("/")
	@Operation(summary = "Get all users")
	public ResponseEntity<?> getAllUsers(){
		try {
			List<UserResponseDTO> userList = userServiceImpl.findAll();
			if(userList.isEmpty())
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found.");
			
			return ResponseEntity.status(HttpStatus.OK).body(userList);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
}
