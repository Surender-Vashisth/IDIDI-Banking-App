package com.ididibank.account_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ididibank.account_management_service.dto.AccountDetailsRequestDto;
import com.ididibank.account_management_service.dto.ApiResponseDto;
import com.ididibank.account_management_service.entity.AccountDetailsEntity;
import com.ididibank.account_management_service.service.AccountManagementService;

@RestController
@RequestMapping("/account-management-service")
public class AccountManagementServiceController {

	@Autowired
	private AccountManagementService accountManagementService;

	@PostMapping("/add-money")
	public ApiResponseDto addMoney(@RequestBody AccountDetailsRequestDto accountDetailsRequestDto) {
		return accountManagementService.addMoney(accountDetailsRequestDto);
	}

	@PostMapping("/withdraw-money")
	public ApiResponseDto withdrawMoney(@RequestBody AccountDetailsRequestDto accountDetailsRequestDto) {
		return accountManagementService.withdrawMoney(accountDetailsRequestDto);
	}

	@GetMapping("/get-account-details/{accountId}")
	public ApiResponseDto getAccountDetails(@PathVariable Integer accountId) {
		return accountManagementService.getAccountDetails(accountId);
	}

	@GetMapping("/get-account-details-by-customerId/{customerId}")
	public AccountDetailsEntity getAccountDetailsByCustomerId(@PathVariable Integer customerId) {
		return accountManagementService.getAccountDetailsByCustomerId(customerId);
	}

	@DeleteMapping("/delete-account/{accountId}")
	public ApiResponseDto deleteAccount(@PathVariable Integer accountId) {
		return accountManagementService.deleteAccount(accountId);
	}
}
