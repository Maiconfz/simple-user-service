/**
 * 
 */
package com.simple_user_service.jdbc_template;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.simple_user_service.model.User;

/**
 * @author MaiconFonsecaZanco
 *
 */
public class UserRowMapper implements RowMapper<User> {
	private static final UserRowMapper userRowMapper = new UserRowMapper();

	private UserRowMapper() {
		super();
	}

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt("id"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("CPF"));
	}

	public static UserRowMapper getInstance() {
		return userRowMapper;
	}

}
