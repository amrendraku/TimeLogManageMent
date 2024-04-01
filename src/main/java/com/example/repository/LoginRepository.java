package com.example.repository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean isValidUser(String username, String password) throws SQLException {
		System.out.println(username);
		String sql = "SELECT COUNT(*) FROM `user` WHERE user_login_id = ? AND password = ?";
		Object[] params = { username, password };
		try {
			int count = jdbcTemplate.queryForObject(sql, params, Integer.class);
			System.out.println(count);
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}

	public String getUserType(String username, String password) {
		String sql = "SELECT user_type FROM `user` WHERE user_login_id = ? AND password = ?";
		Object[] params = { username, password };
		try {
			return jdbcTemplate.queryForObject(sql, params, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
}
