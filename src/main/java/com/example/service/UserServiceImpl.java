package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;

import com.example.repository.UserRepository;
import com.example.requestmodel.Login;
import com.example.requestmodel.UserRequest;
import com.example.response.LoginResponse;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public String register(UserRequest userRequest) { //
		User user = new User(userRequest.getUserId(), userRequest.getName(), userRequest.getEmail(),
				this.passwordEncoder.encode(userRequest.getPassword()));
		User user1 = userRepository.findByEmail(userRequest.getEmail());
		if (user1 != null) {
			return new String("User is already registered.");
		}
		userRepository.save(user);
		return "User Registered Successfully";
	}

	
	@Override
	public LoginResponse loginUser(Login login) {
		User user1 = userRepository.findByEmail(login.getEmail());
		if (user1 != null) {
			String password = login.getPassword();
			String encodedPassword = user1.getPassword();
			Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
			if (isPwdRight) {
				Optional<User> user = userRepository.findOneByEmailAndPassword(login.getEmail(), encodedPassword);
				if (user.isPresent()) {
					return new LoginResponse("Login Success", true);
				} else {
					return new LoginResponse("Login Failed", false);
				}
			} else {

				return new LoginResponse("password Not Match", false);
			}
		} else {

			return new LoginResponse("Email not exits", false);
		}
	}
}
