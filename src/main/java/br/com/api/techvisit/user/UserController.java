package br.com.api.techvisit.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.user.definition.UserDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/get-all")
	public List<UserDTO> getAll() {
		return this.userService.getAll();
	}

	@PutMapping()
	public UserDTO update(@RequestBody @Valid UserDTO user) {
		return this.userService.update(user);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestBody List<Long> ids) {
		this.userService.delete(ids);
	}

	@PutMapping("/update-password")
	public UserDTO updatePassword(@RequestBody AuthenticationDTO loginInfo) {
		return this.userService.updatePassword(loginInfo);
	}

}
