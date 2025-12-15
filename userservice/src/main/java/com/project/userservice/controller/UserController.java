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

import com.project.userservice.constants.AppConstants;
import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;
import com.project.userservice.serviceImpl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstants.BASE_URL)
@Tag(name = AppConstants.CONTROLLER_TITLE, description = AppConstants.CONTROLLER_DESC)
public class UserController {
	
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}
	
	@PostMapping("/")
	@Operation(summary = AppConstants.CREATE_USER_API)
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){
			User savedUser = userServiceImpl.saveUser(userDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(savedUser.getName(), savedUser.getEmail()));	
	}
	
	@GetMapping("/{id}")
	@Operation(summary = AppConstants.GET_USER_API)
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
	        User user = userServiceImpl.findById(id);
	        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user.getName(), user.getEmail()));
	        
	}

	@GetMapping("/")
	@Operation(summary = AppConstants.GET_ALL_USERS_API)
	public ResponseEntity<?> getAllUsers() {
	    List<UserResponseDTO> userList = userServiceImpl.findAll();
	    return ResponseEntity.status(HttpStatus.OK).body(userList);
	}
	
}
