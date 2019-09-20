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
	
	/**
	 * メールアドレスから管理者情報を取得する.
	 * 
	 * @param email メールアドレス
	 * @return 管理者情報 存在しない場合nullを返す
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
//	public User findByMailAddress(String email, String password) {
//		String sql = "select id,name,email,password,zipcode,address,telephone from users where email=:email and password=:password";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password",password);
//		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
//		if (userList.size() == 0) {
//			return null;
//		}
//		return userList.get(0);
//	}
	
}
