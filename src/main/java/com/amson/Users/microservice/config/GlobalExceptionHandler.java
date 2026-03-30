package com.amson.Users.microservice.config;

import com.amson.Users.microservice.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	ProblemDetail handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problem.setTitle("User not found");
		problem.setType(URI.create("https://users-microservice/problems/user-not-found"));
		problem.setProperty("userId", ex.getUserId());
		if (request.getRequestURI() != null) {
			problem.setInstance(URI.create(request.getRequestURI()));
		}
		return problem;
	}

}
