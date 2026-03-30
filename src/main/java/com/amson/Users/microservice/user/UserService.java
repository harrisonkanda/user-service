package com.amson.Users.microservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public PaginatedUsersResponse findAll(Pageable pageable) {
		Page<User> page = userRepository.findAll(pageable);
		Page<UserResponse> mapped = page.map(UserResponse::from);
		return PaginatedUsersResponse.from(mapped);
	}

	@Transactional(readOnly = true)
	public UserResponse findById(Long id) {
		return userRepository.findById(id)
				.map(UserResponse::from)
				.orElseThrow(() -> new UserNotFoundException(id));
	}

}
