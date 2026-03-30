package com.amson.Users.microservice.user;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Spring Data-style page of users with navigation metadata.")
public record PaginatedUsersResponse(
		@Schema(description = "Users belonging to the current page.")
		List<UserResponse> content,
		@Schema(description = "Zero-based page index.", example = "0")
		int page,
		@Schema(description = "Maximum number of elements in this page.", example = "20")
		int size,
		@Schema(description = "Total number of users matching the query across all pages.", example = "100")
		long totalElements,
		@Schema(description = "Total number of pages available.", example = "5")
		int totalPages,
		@Schema(description = "True if this is the first page.")
		boolean first,
		@Schema(description = "True if this is the last page.")
		boolean last) {

	static PaginatedUsersResponse from(Page<UserResponse> page) {
		return new PaginatedUsersResponse(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.isFirst(),
				page.isLast());
	}

}
