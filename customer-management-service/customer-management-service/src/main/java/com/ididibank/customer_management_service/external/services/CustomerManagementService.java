package com.ididibank.customer_management_service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ididibank.customer_management_service.entity.CustomerDetailsEntity;

@FeignClient(name = "CUSTOMER-MANAGEMENT-SERVICE")
public interface CustomerManagementService {

	@GetMapping("/customer-management-service/get-customer-details/{customerId}")
	CustomerDetailsEntity getCustomerDetails(@PathVariable Integer customerId);
}
