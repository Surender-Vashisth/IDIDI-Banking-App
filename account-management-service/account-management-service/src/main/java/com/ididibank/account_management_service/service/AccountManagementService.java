package com.ididibank.account_management_service.service;

import org.springframework.stereotype.Service;

import com.ididibank.account_management_service.dto.AccountDetailsRequestDto;
import com.ididibank.account_management_service.dto.ApiResponseDto;
import com.ididibank.account_management_service.entity.AccountDetailsEntity;

@Service
public interface AccountManagementService {

	public ApiResponseDto addMoney(AccountDetailsRequestDto accountDetailsRequestDto);

	public ApiResponseDto withdrawMoney(AccountDetailsRequestDto accountDetailsRequestDto);

	public ApiResponseDto getAccountDetails(Integer accountId);

	public ApiResponseDto deleteAccount(Integer accountId);

	public AccountDetailsEntity getAccountDetailsByCustomerId(Integer customerId);
}
