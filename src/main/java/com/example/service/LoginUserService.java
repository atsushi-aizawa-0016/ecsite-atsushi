package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.LoginUserRepository;

@Service
public class LoginUserService {
	
	@Autowired
	private LoginUserRepository loginUserRepository;

	public User login(RegisterUserForm form) {
		return loginUserRepository.findByMailAddress(form.getEmail(), form.getPassword());
	}
}
