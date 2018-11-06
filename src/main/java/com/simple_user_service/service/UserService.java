/**
 * 
 */
package com.simple_user_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.simple_user_service.jdbc_template.UserRowMapper;
import com.simple_user_service.model.User;

/**
 * @author MaiconFonsecaZanco
 *
 */
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 */
	@Autowired
	public UserService(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public int save(User user) {
		log.info(String.format("Saving User: %s", user.toString()));
		if (user.getId() == null) {
			return this.jdbcTemplate.update("INSERT INTO TB_USER(FIRST_NAME, LAST_NAME, CPF) VALUES (?, ?, ?)",
					user.getFirstName(), user.getLastName(), user.getCpf());
		} else {
			return this.jdbcTemplate.update("UPDATE TB_USER SET FIRST_NAME = ?, LAST_NAME = ?, CPF = ? WHERE ID = ?",
					user.getFirstName(), user.getLastName(), user.getCpf(), user.getId());
		}
	}

	public List<User> findAll() {
		return jdbcTemplate.query("SELECT ID, FIRST_NAME, LAST_NAME, CPF FROM TB_USER", UserRowMapper.getInstance());
	}

	public User findByCPF(String cpf) {
		return jdbcTemplate.queryForObject("SELECT ID, FIRST_NAME, LAST_NAME, CPF FROM TB_USER WHERE CPF = ?",
				new Object[] { cpf }, UserRowMapper.getInstance());
	}
}
