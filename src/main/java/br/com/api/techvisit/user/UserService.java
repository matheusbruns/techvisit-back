package br.com.api.techvisit.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.user.definition.UserDTO;
import br.com.api.techvisit.user.exception.UserNotFoundException;
import br.com.api.techvisit.user.factory.UserFactory;
import br.com.api.techvisit.user.model.UserModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	OrganizationService organizationService;
	
	public List<UserDTO> getAll() {
		return new UserFactory().build(this.userRepository.findAll());
	}

	@Transactional
	public UserDTO update(@Valid UserDTO data) {
		UserModel user = this.userRepository.findById(data.getId()).orElseThrow(() -> new UserNotFoundException("User not found!"));
		OrganizationModel organization = this.organizationService.getOrganizationById(data.getOrganization().getId()).orElseThrow(() -> new OrganizationNotFoundException("Organizatin not found."));
		
		
		this.userRepository.save(new UserFactory().buildUpdate(user, data, organization));
		return data;
	}

	@Transactional
	public void delete(List<Long> ids) {
		this.userRepository.deleteAllById(ids);
	}

}
