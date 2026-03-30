package com.amson.Users.microservice.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "User representation returned by the API (nine business fields plus id).")
public record UserResponse(
		@Schema(description = "Surrogate primary key allocated by the database.", example = "1")
		Long id,
		@Schema(description = "Unique contact email.", example = "alice.johnson@example.com")
		String email,
		@Schema(description = "Display name of the user.", example = "Alice Johnson")
		String fullName,
		@Schema(description = "Primary phone number in E.164-style format if available.", example = "+1-555-0101")
		String phoneNumber,
		@Schema(description = "Lifecycle flag: ACTIVE or INACTIVE.", example = "ACTIVE", allowableValues = { "ACTIVE", "INACTIVE" })
		String status,
		@Schema(description = "Country of residence or primary location.", example = "United States")
		String country,
		@Schema(description = "Job title or primary role.", example = "Software Engineer")
		String jobTitle,
		@Schema(description = "Timestamp when the record was created (UTC).")
		Instant createdAt,
		@Schema(description = "Timestamp of the last update (UTC).")
		Instant updatedAt) {

	static UserResponse from(User user) {
		return new UserResponse(
				user.getId(),
				user.getEmail(),
				user.getFullName(),
				user.getPhoneNumber(),
				user.getStatus(),
				user.getCountry(),
				user.getJobTitle(),
				user.getCreatedAt(),
				user.getUpdatedAt());
	}

}
