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
	public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
		GetUserResponse response = new GetUserResponse();

		com.simple_user_service.model.User user = this.userService.findByCPF(request.getCpf());

		User responseUser = getWsUserFrom(user);

		com.simple_user_service.model.Address address = this.addressService.findByUserId(user.getId());

		Address responseAddress = getWsAddressFrom(address);

		responseUser.setAddress(responseAddress);
		response.setUser(responseUser);

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
	@ResponsePayload
	public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
		GetAllUsersResponse response = new GetAllUsersResponse();
		this.userService.findAll().forEach(user -> response.getUser().add(this.getWsUserFrom(user)));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveUserRequest")
	@ResponsePayload
	public SaveUserResponse saveUser(@RequestPayload SaveUserRequest request) {
		SaveUserResponse response = new SaveUserResponse();
		try {
			this.userService.save(this.getModelUserFrom(request.getUser()));

			com.simple_user_service.model.User persistedUser = this.userService.findByCPF(request.getUser().getCpf());

			this.addressService.save(this.getModelAddressFrom(request.getUser().getAddress(), persistedUser));

			response.setStatus("SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("ERROR: " + String.valueOf(e));
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
	@ResponsePayload
	public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
		DeleteUserResponse response = new DeleteUserResponse();
		try {
			if (request.getUser() != null) {
				com.simple_user_service.model.User modelUser = this.getModelUserFrom(request.getUser());
				this.userService.delete(modelUser);
				this.addressService.delete(new com.simple_user_service.model.Address(null, null, null, null, null, null,
						null, null, modelUser));
			}
			response.setStatus("SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("ERROR: " + String.valueOf(e));
		}

		return response;
	}

	private User getWsUserFrom(com.simple_user_service.model.User user) {
		User wsUser = new User();
		wsUser.setId(BigInteger.valueOf(user.getId().longValue()));
		wsUser.setFirstName(user.getFirstName());
		wsUser.setLastName(user.getLastName());
		wsUser.setCpf(user.getCpf());
		return wsUser;
	}

	private Address getWsAddressFrom(com.simple_user_service.model.Address address) {
		Address responseAddress = new Address();
		responseAddress.setId(BigInteger.valueOf(address.getId().longValue()));
		responseAddress.setPostalCode(BigInteger.valueOf(address.getPostalCode().longValue()));
		responseAddress.setStreet(address.getStreet());
		responseAddress.setNumber(BigInteger.valueOf(address.getNumber().longValue()));
		responseAddress.setDistrict(address.getDistrict());
		responseAddress.setCity(address.getCity());
		responseAddress.setState(address.getState());
		responseAddress.setCountry(address.getCountry());
		return responseAddress;
	}

	private com.simple_user_service.model.User getModelUserFrom(User user) {
		return new com.simple_user_service.model.User(user.getId() != null ? user.getId().intValue() : null,
				user.getFirstName(), user.getLastName(), user.getCpf());
	}

	private com.simple_user_service.model.Address getModelAddressFrom(Address address,
			com.simple_user_service.model.User modelUser) {
		return new com.simple_user_service.model.Address(address.getId() != null ? address.getId().intValue() : null,
				address.getPostalCode() != null ? address.getPostalCode().intValue() : null, address.getStreet(),
				address.getNumber() != null ? address.getNumber().intValue() : null, address.getDistrict(),
				address.getCity(), address.getState(), address.getCountry(), modelUser);
	}

}
