package br.com.api.techvisit.authentication.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	static final String ERROR_CODE = "errorCode";
	static final String MESSAGE = "message";

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_CODE, "INVALID_CREDENTIALS");
		error.put(MESSAGE, ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Map<String, String>> handleDisabledException(DisabledException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_CODE, "USER_INACTIVE");
		error.put(MESSAGE, "Usuário inativo.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleInternalServerError(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_CODE, "INTERNAL_SERVER_ERROR");
		error.put(MESSAGE, "Ocorreu um erro interno no servidor.");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
