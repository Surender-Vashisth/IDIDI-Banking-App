package com.ididibank.customer_management_service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ididibank.customer_management_service.dto.AccountDetailsRequestDto;
import com.ididibank.customer_management_service.dto.ApiResponseDto;
import com.ididibank.customer_management_service.entity.CustomerDetailsEntity;

@FeignClient(name = "ACCOUNT-MANAGEMENT-SERVICE")
public interface AccountManagementService {

	@GetMapping("/account-management-service/get-customer-details/{customerId}")
	CustomerDetailsEntity getCustomerDetails(@PathVariable Integer customerId);

	@PostMapping("/account-management-service/add-money")
	public ApiResponseDto addMoney(@RequestBody AccountDetailsRequestDto accountDetailsRequestDto);
}
