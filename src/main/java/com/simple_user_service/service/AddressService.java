package com.simple_user_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.simple_user_service.jdbc_template.AddressRowMapper;
import com.simple_user_service.model.Address;

/**
 * @author MaiconFonsecaZanco
 *
 */
@Service
public class AddressService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 */
	@Autowired
	public AddressService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int save(Address address) {
		log.info(String.format("Inserting Address: %s", address.toString()));
		return this.jdbcTemplate.update(
				"INSERT INTO TB_ADDRESS(POSTAL_CODE, STREET, NUMBER, DISTRICT, CITY, STATE, COUNTRY, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				address.getPostalCode(), address.getStreet(), address.getNumber(), address.getDistrict(),
				address.getCity(), address.getState(), address.getCountry(), address.getUser().getId());
	}

	public List<Address> findAll() {
		return jdbcTemplate.query(
				"SELECT AD.ID, AD.POSTAL_CODE, AD.STREET, AD.NUMBER, AD.DISTRICT, AD.CITY, AD.STATE, AD.COUNTRY, U.ID AS USER_ID, U.FIRST_NAME AS USER_FIRST_NAME, U.LAST_NAME AS USER_LAST_NAME, U.CPF AS USER_CPF FROM TB_ADDRESS AD JOIN TB_USER U ON AD.USER_ID = U.ID",
				AddressRowMapper.getInstance());
	}

	public Address findByUserId(Integer id) {
		return jdbcTemplate.queryForObject(
				"SELECT AD.ID, AD.POSTAL_CODE, AD.STREET, AD.NUMBER, AD.DISTRICT, AD.CITY, AD.STATE, AD.COUNTRY, U.ID AS USER_ID, U.FIRST_NAME AS USER_FIRST_NAME, U.LAST_NAME AS USER_LAST_NAME, U.CPF AS USER_CPF FROM TB_ADDRESS AD JOIN TB_USER U ON AD.USER_ID = U.ID WHERE AD.USER_ID = ?",
				new Object[] { id }, AddressRowMapper.getInstance());
	}

}
