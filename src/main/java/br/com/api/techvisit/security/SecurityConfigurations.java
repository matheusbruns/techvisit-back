package br.com.api.techvisit.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import br.com.api.techvisit.authentication.exception.CustomAuthenticationEntryPoint;
import br.com.api.techvisit.user.definition.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityFilter securityFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(this.allowedOrigins());
                    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
						.requestMatchers("/actuator/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/register").hasRole(UserRole.ADMIN.getRole())
						.requestMatchers("/organization").hasRole(UserRole.ADMIN.getRole())
						.requestMatchers(HttpMethod.GET, "/user/get-all").hasRole(UserRole.ADMIN.getRole())
						.requestMatchers(HttpMethod.DELETE, "/user").hasRole(UserRole.ADMIN.getRole())
						.requestMatchers(HttpMethod.GET, "/my-visits").hasRole(UserRole.TECHNICIAN.getRole())
						.requestMatchers(HttpMethod.PUT, "/my-visits/update").hasRole(UserRole.TECHNICIAN.getRole())
                        .requestMatchers("/user", "/user/**").hasRole(UserRole.ADMIN.getRole())
						.anyRequest().authenticated()
				)
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(customAuthenticationEntryPoint))
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	private List<String> allowedOrigins(){
		return List.of("http://localhost:3000", "https://techvisit-front.vercel.app/", "https://techvisit.tech/");
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
