package com.project.userservice.serviceImpl;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(UserDTO userDTO) throws Exception {
		User user = new User(userDTO.getName(), userDTO.getEmail(), encode(userDTO.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
	    return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<UserResponseDTO> findAll() {
		return userRepository.findAll().stream().map(user -> new UserResponseDTO(user.getName(), user.getEmail())).collect(Collectors.toList());
	}

	@Override
	public User updateUser(User user) {
		if(!userRepository.existsById(user.getId()))
				return null;
		
		return userRepository.save(user);
	}
	
	public String encode(String password) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] hash = digest.digest(password.getBytes("UTF-8"));
	    return Base64.getEncoder().encodeToString(hash);
	}

}
