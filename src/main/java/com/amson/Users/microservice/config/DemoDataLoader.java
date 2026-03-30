package com.amson.Users.microservice.config;

import com.amson.Users.microservice.user.User;
import com.amson.Users.microservice.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("!test")
public class DemoDataLoader implements ApplicationRunner {

	private final UserRepository userRepository;

	public DemoDataLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) {
		if (userRepository.count() > 0) {
			return;
		}
		userRepository.save(user("alice.johnson@example.com", "Alice Johnson", "+1-555-0101", "ACTIVE",
				"United States", "Software Engineer"));
		userRepository.save(user("bob.smith@example.com", "Bob Smith", "+44-20-7946-0958", "ACTIVE",
				"United Kingdom", "Product Manager"));
		userRepository.save(user("carla.mendez@example.com", "Carla Mendez", "+34-91-123-4567", "ACTIVE",
				"Spain", "Data Analyst"));
		userRepository.save(user("david.kim@example.com", "David Kim", "+82-2-555-0199", "ACTIVE",
				"South Korea", "DevOps Engineer"));
		userRepository.save(user("elena.petrova@example.com", "Elena Petrova", "+7-495-123-4567", "INACTIVE",
				"Russia", "UX Designer"));
		userRepository.save(user("frank.njoroge@example.com", "Frank Njoroge", "+254-712-345678", "ACTIVE",
				"Kenya", "Solutions Architect"));
		userRepository.save(user("greta.bauer@example.com", "Greta Bauer", "+49-30-12345678", "ACTIVE",
				"Germany", "QA Lead"));
		userRepository.save(user("hiro.tanaka@example.com", "Hiro Tanaka", "+81-3-1234-5678", "ACTIVE",
				"Japan", "Backend Developer"));
		userRepository.save(user("isabel.costa@example.com", "Isabel Costa", "+55-11-91234-5678", "ACTIVE",
				"Brazil", "Engineering Manager"));
		userRepository.save(user("james.omalley@example.com", "James O'Malley", "+353-1-234-5678", "ACTIVE",
				"Ireland", "Security Engineer"));
	}

	private static User user(String email, String fullName, String phone, String status, String country, String jobTitle) {
		User u = new User();
		u.setEmail(email);
		u.setFullName(fullName);
		u.setPhoneNumber(phone);
		u.setStatus(status);
		u.setCountry(country);
		u.setJobTitle(jobTitle);
		return u;
	}

}
