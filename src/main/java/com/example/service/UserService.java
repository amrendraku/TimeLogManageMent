package com.example.service;

import org.springframework.stereotype.Component;

import com.example.requestmodel.Login;
import com.example.requestmodel.UserRequest;
import com.example.response.LoginResponse;

@Component
public interface UserService {
	public String register(UserRequest user);

	public LoginResponse loginUser(Login login);

}
