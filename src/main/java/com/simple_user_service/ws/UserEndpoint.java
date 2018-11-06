/**
 * 
 */
package com.simple_user_service.ws;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.simple_user_service.service.AddressService;
import com.simple_user_service.service.UserService;

/**
 * @author MaiconFonsecaZanco
 *
 */
@Endpoint
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://ws.simple-user-service.com";

	private final UserService userService;
	private final AddressService addressService;

	/**
	 * @param userService
	 * @param addressService
	 */
	@Autowired
	public UserEndpoint(UserService userService, AddressService addressService) {
		this.userService = userService;
		this.addressService = addressService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
	@ResponsePayload
	public GetUserResponse getCountry(@RequestPayload GetUserRequest request) {
		GetUserResponse response = new GetUserResponse();

		User responseUser = new User();
		com.simple_user_service.model.User user = this.userService.findByCPF(request.getCpf());

		responseUser.setId(BigInteger.valueOf(user.getId().longValue()));
		responseUser.setFirstName(user.getFirstName());
		responseUser.setLastName(user.getLastName());
		responseUser.setCpf(user.getCpf());

		Address responseAddress = new Address();
		response.setUser(responseUser);

		return response;
	}

}
