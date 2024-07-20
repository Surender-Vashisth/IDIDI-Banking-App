package com.ididibank.customer_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ididibank.customer_management_service.dto.ApiResponseDto;
import com.ididibank.customer_management_service.dto.CustomerDetailsRequestDto;
import com.ididibank.customer_management_service.service.CustomerManagementService;

@RestController
@RequestMapping("/customer-management-service")
public class CustomerManagementServiceController {

	@Autowired
	private CustomerManagementService customerManagementService;

	@PostMapping("/add-customer")
	public ApiResponseDto addCustomer(@RequestBody CustomerDetailsRequestDto customerDetailsDto) {
		return customerManagementService.addCustomer(customerDetailsDto);
	}

	@PutMapping("/update-customer")
	public ApiResponseDto updateCustomer(@RequestBody CustomerDetailsRequestDto customerDetailsDto) {
		return customerManagementService.updateCustomer(customerDetailsDto);
	}

	@GetMapping("/get-all-customers")
	public ApiResponseDto getAllCustomers() {
		return customerManagementService.getAllCustomers();
	}

	@GetMapping("/get-customer-details/{customerId}")
	public ApiResponseDto getCustomerDetails(@PathVariable Integer customerId) {
		return customerManagementService.getCustomerDetails(customerId);
	}

	@DeleteMapping("/delete-customer/{customerId}")
	public ApiResponseDto deleteCustomer(@PathVariable Integer customerId) {
		return customerManagementService.deleteCustomer(customerId);
	}
}
