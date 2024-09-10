package br.com.api.techvisit.user.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.techvisit.generic.GenericModel;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.user.definition.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserModel extends GenericModel implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column(name = "login", nullable = false, unique = true)
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "organization_id")
	private OrganizationModel organization;

	@Column(name = "role")
	private UserRole role;

	@Column(name = "is_active")
	private boolean isActive;

	public UserModel(String login, String password, UserRole role, OrganizationModel organization, LocalDate creationDate, boolean isActive) {
		this.login = login;
		this.password = password;
		this.role = role;
		this.organization = organization;
		this.creationDate = creationDate;
		this.isActive = isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return login;
	}

}
