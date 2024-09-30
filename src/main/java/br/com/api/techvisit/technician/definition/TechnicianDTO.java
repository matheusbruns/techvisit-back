package br.com.api.techvisit.technician.definition;

import lombok.Data;

@Data
public class TechnicianDTO {

	private Long id;

	private String name;

	private String login;

	private String cpf;

	private String email;

	private String phoneNumber;

	private Boolean active;

}
