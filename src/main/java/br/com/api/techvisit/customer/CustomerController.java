package br.com.api.techvisit.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.exception.CannotDeleteCustomerException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerDTO> getAllCustomers(@RequestParam("organization") Long organizationId) {
		return this.customerService.getAllByOrganization(organizationId);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO save(@RequestBody CustomerDTO customerBean) {
		return this.customerService.save(customerBean);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> delete(@RequestBody List<Long> ids) {
	    try {
	        this.customerService.delete(ids);
	        return ResponseEntity.ok("Clientes excluídos com sucesso.");
	    } catch (CannotDeleteCustomerException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Existem dados vinculados. Não é possível excluir os clientes.");
	    }
	}


}
