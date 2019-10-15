package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));	
		return user;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public User login(User user) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", user.getEmail()).addValue("password", user.getPassword()) ;
		String sql = "SELECT \n" + 
				"id,name,email,password,zipcode,address,telephone \n" + 
				"FROM \n" + 
				"users \n" + 
				"WHERE email = :email \n" + 
				"and password = :password\n" + 
				";";
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}else {
			return userList.get(0);			
		}
	}
	
	/**
	 * 管理者情報の挿入.
	 * 
	 * @param user
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users(name, email, password, zipcode, address, telephone)values(:name, :email, :password, :zipcode, :address, :telephone);";
		template.update(sql, param);
	}
	
	public User findByUserId(Integer userId) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		String sql = "SELECT \n" + 
				"id,name,email,password,zipcode,address,telephone \n" + 
				"FROM \n" + 
				"users \n" + 
				"WHERE id = :userId \n" + 
				";";
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}else {
			return userList.get(0);			
		}
	}
	
	public User findByEmail(String email) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		String sql = "SELECT \n" + 
				"id,name,email,password,zipcode,address,telephone \n" + 
				"FROM \n" + 
				"users \n" + 
				"WHERE email = :email \n" + 
				";";
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}else {
			return userList.get(0);			
		}
	}
	
}
