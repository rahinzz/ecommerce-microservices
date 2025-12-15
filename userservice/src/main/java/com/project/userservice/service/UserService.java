package com.project.userservice.service;

import java.util.List;

import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;

public interface UserService {
	
	public User saveUser(UserDTO user);
	
	public User findById(Long id);
	
	public List<UserResponseDTO> findAll();
	
	public User updateUser(User user);

}
