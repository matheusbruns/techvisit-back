package br.com.api.techvisit.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllCustomers(@RequestParam("organization") Long organizationId) {
		return this.customerService.getAllByOrganization(organizationId);
	}

}
