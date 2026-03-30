package com.amson.Users.microservice.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
		name = "Users",
		description = "V1. Read-only user directory. All responses are JSON. "
				+ "Endpoints are intentionally unsecured for this milestone.")
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(
			summary = "List users (paginated)",
			description = """
					Returns a page of users sorted by default on ascending `id`. \
					Use standard Spring Data query parameters: `page` (zero-based), `size`, and `sort` \
					(for example `sort=fullName,asc`). Maximum page size is enforced via application configuration.""")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Page retrieved successfully",
					content = @Content(schema = @Schema(implementation = PaginatedUsersResponse.class)))
	})
	@GetMapping
	public PaginatedUsersResponse list(
			@Parameter(hidden = true)
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return userService.findAll(pageable);
	}

	@Operation(
			summary = "Get user by id",
			description = "Loads a single user by their numeric primary key. "
					+ "Returns RFC 9457 `ProblemDetail` JSON when the identifier is unknown.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "User located",
					content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(
					responseCode = "404",
					description = "No user with the supplied id",
					content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class)))
	})
	@GetMapping("/{id}")
	public UserResponse getById(
			@Parameter(description = "Primary key of the user to fetch.", required = true, example = "1")
			@PathVariable("id") Long id) {
		return userService.findById(id);
	}

}
