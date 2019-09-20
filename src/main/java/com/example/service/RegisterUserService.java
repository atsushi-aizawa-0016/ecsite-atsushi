package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
public class RegisterUserService {

	@Autowired
	private UserRepository registerUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 管理者情報の登録をする.
	 * パスワードはここでハッシュ化する.
	 * 
	 * @param user 管理者情報
	 */
	public void insert(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		registerUserRepository.insert(user);
	}
	
	/**
	 * メールアドレスから管理者情報を登録する.
	 * 
	 * @param email メールアドレス
	 * @return 管理者情報 存在しなければnullが返る
	 */
	public User findByEmail(String email) {
		return registerUserRepository.findByEmail(email);
	}
}
