package br.com.api.techvisit.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.customer.bean.CustomerBean;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerBean> getAllCustomers(@RequestParam("organization") Long organizationId) {
		return this.customerService.getAllByOrganization(organizationId);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerBean save(@RequestBody CustomerBean customerBean) {
		return this.customerService.save(customerBean);
	}

}
