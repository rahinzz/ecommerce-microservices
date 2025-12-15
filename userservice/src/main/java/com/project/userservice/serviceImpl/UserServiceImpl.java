package com.project.userservice.serviceImpl;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.userservice.constants.AppConstants;
import com.project.userservice.dto.UserDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.exceptionhandler.UserAlreadyExistsException;
import com.project.userservice.exceptionhandler.UserNotFoundException;
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
	public User saveUser(UserDTO userDTO) {
	    String encodedPassword;
	    
	    if(userRepository.existsByNameAndEmail(userDTO.getName(), userDTO.getEmail())) {
	    	throw new UserAlreadyExistsException(String.format(AppConstants.USER_ALREADY_EXISTS, userDTO.getName(), userDTO.getEmail()));
	    }

	    try {
	        encodedPassword = encode(userDTO.getPassword());
	    } catch (Exception ex) {
	        throw new RuntimeException(AppConstants.PASSWORD_ENCODING_ERROR);
	    }

	    return userRepository.save(new User(userDTO.getName(), userDTO.getEmail(), encodedPassword));
	}

	@Override
	public User findById(Long id) {
	    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(AppConstants.USR_NOT_FOUND_BY_ID + id));
	}

	@Override
	public List<UserResponseDTO> findAll() {
		return userRepository.findAll().stream().map(user -> new UserResponseDTO(user.getName(), user.getEmail())).collect(Collectors.toList());
	}

	@Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(AppConstants.USR_NOT_FOUND_BY_ID + user.getId());
        }
        return userRepository.save(user);
    }
	
	public String encode(String password) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(AppConstants.HASH_ALGO_SHA256);
	    byte[] hash = digest.digest(password.getBytes(AppConstants.UTF_8));
	    return Base64.getEncoder().encodeToString(hash);
	}

}
