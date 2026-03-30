package com.amson.Users.microservice.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@BeforeEach
	void seedUsers() {
		for (int i = 0; i < 25; i++) {
			User user = new User();
			user.setEmail("user" + i + "@example.com");
			user.setFullName("User " + i);
			user.setPhoneNumber("+100000000" + i);
			user.setStatus(i % 5 == 0 ? "INACTIVE" : "ACTIVE");
			user.setCountry("Testland");
			user.setJobTitle("Tester");
			userRepository.save(user);
		}
	}

	@Test
	void listUsers_returnsPaginatedFirstPage() throws Exception {
		mockMvc.perform(get("/api/users").param("size", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(10))
				.andExpect(jsonPath("$.page").value(0))
				.andExpect(jsonPath("$.size").value(10))
				.andExpect(jsonPath("$.totalElements").value(25))
				.andExpect(jsonPath("$.totalPages").value(3))
				.andExpect(jsonPath("$.first").value(true))
				.andExpect(jsonPath("$.last").value(false));
	}

	@Test
	void listUsers_secondPage() throws Exception {
		mockMvc.perform(get("/api/users").param("page", "2").param("size", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(5))
				.andExpect(jsonPath("$.page").value(2))
				.andExpect(jsonPath("$.first").value(false))
				.andExpect(jsonPath("$.last").value(true));
	}

	@Test
	void getUserById_returnsUser() throws Exception {
		User saved = userRepository.findAll().getFirst();

		mockMvc.perform(get("/api/users/" + saved.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(saved.getId()))
				.andExpect(jsonPath("$.email").value(saved.getEmail()))
				.andExpect(jsonPath("$.fullName").value(saved.getFullName()))
				.andExpect(jsonPath("$.status").value(saved.getStatus()));
	}

	@Test
	void getUserById_unknownId_returns404() throws Exception {
		long missingId = 9_999_999L;
		mockMvc.perform(get("/api/users/" + missingId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("User not found"))
				.andExpect(jsonPath("$.detail").exists())
				.andExpect(jsonPath("$.userId").value(missingId));
	}

}
