package br.com.api.techvisit.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.api.techvisit.user.definition.UserModel;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	public String secret;
	
	public String generateToken(UserModel user) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("auth-api")
					.withSubject(user.getLogin())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new JWTCreationException("Error while generating token", exception);
		}

	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}
	
	protected Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
	}

}
