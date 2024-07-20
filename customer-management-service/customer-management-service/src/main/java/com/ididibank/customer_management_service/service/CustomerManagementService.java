package com.ididibank.customer_management_service.service;

import org.springframework.stereotype.Service;

import com.ididibank.customer_management_service.dto.ApiResponseDto;
import com.ididibank.customer_management_service.dto.CustomerDetailsRequestDto;

@Service
public interface CustomerManagementService {

	public ApiResponseDto addCustomer(CustomerDetailsRequestDto customerDetailsDto);

	public ApiResponseDto updateCustomer(CustomerDetailsRequestDto customerDetailsDto);

	public ApiResponseDto getAllCustomers();

	public ApiResponseDto getCustomerDetails(Integer customerId);

	public ApiResponseDto deleteCustomer(Integer customerId);
}
