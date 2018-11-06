package com.simple_user_service.jdbc_template;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.simple_user_service.model.Address;
import com.simple_user_service.model.User;

public class AddressRowMapper implements RowMapper<Address> {

	private static final AddressRowMapper addressRowMapper = new AddressRowMapper();

	private AddressRowMapper() {
		super();
	}

	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Address(new Integer(rs.getInt("ID")), new Integer(rs.getInt("POSTAL_CODE")), rs.getString("STREET"),
				new Integer(rs.getInt("NUMBER")), rs.getString("DISTRICT"), rs.getString("CITY"), rs.getString("STATE"),
				rs.getString("COUNTRY"), new User(new Integer(rs.getInt("USER_ID")), rs.getString("USER_FIRST_NAME"),
						rs.getString("USER_LAST_NAME"), rs.getString("USER_CPF")));
	}

	public static AddressRowMapper getInstance() {
		return addressRowMapper;
	}

}
