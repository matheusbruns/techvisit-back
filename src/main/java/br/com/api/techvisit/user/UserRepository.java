package br.com.api.techvisit.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.techvisit.user.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

	Optional<UserDetails> findByLogin(String login);
	
	@Query("Select user from UserModel user where user.login = :login")
	Optional<UserModel> findUserByLogin(String login);

	
}
