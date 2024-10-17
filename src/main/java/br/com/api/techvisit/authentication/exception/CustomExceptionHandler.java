package br.com.api.techvisit.authentication.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class CustomExceptionHandler {

	final String errorCode = "errorCode";
	final String message = "message";

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(errorCode, "INVALID_CREDENTIALS");
		error.put(message, ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Map<String, String>> handleDisabledException(DisabledException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(errorCode, "USER_INACTIVE");
		error.put(message, "Usuário inativo.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<Map<String, String>> handleTokenExpiredException(TokenExpiredException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(errorCode, "TOKEN_EXPIRED");
		error.put(message, "Sessão expirada.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(errorCode, "ACCESS_DENIED");
		error.put(message, "Acesso negado.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

}
