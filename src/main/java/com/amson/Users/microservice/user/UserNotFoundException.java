package com.amson.Users.microservice.user;

public class UserNotFoundException extends RuntimeException {

	private final Long userId;

	public UserNotFoundException(Long userId) {
		super("No user exists with id " + userId);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

}
