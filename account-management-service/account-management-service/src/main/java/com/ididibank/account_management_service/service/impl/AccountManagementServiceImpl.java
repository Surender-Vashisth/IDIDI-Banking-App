package com.ididibank.account_management_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ididibank.account_management_service.dto.AccountDetailsRequestDto;
import com.ididibank.account_management_service.dto.ApiResponseDto;
import com.ididibank.account_management_service.entity.AccountDetailsEntity;
import com.ididibank.account_management_service.entity.CustomerDetailsEntity;
import com.ididibank.account_management_service.repository.AccountManagementServiceRepository;
import com.ididibank.account_management_service.service.AccountManagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountManagementServiceImpl implements AccountManagementService {

	@Autowired
	private AccountManagementServiceRepository accountManagementServiceRepository;

	@Override
	public ApiResponseDto addMoney(AccountDetailsRequestDto accountDetailsRequestDto) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			AccountDetailsEntity savedAccountDetailsEntity = accountManagementServiceRepository
					.findByCustomerId(accountDetailsRequestDto.getCustomerId());
			if (savedAccountDetailsEntity != null) {
				Double balance = savedAccountDetailsEntity.getBalance().doubleValue()
						+ accountDetailsRequestDto.getBalance().doubleValue();
				savedAccountDetailsEntity.setBalance(balance);
				accountManagementServiceRepository.save(savedAccountDetailsEntity);
				apiResponseDto.setMessage("money credited");
				apiResponseDto.setStatus(HttpStatus.OK);
			} else {
				AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity();
				accountDetailsEntity.setAccountNumber(accountDetailsRequestDto.getAccountNumber());
				accountDetailsEntity.setBalance(1000.00);
				accountDetailsEntity.setBranch(accountDetailsRequestDto.getBranch());
				accountDetailsEntity.setCustomerId(accountDetailsRequestDto.getCustomerId());
				accountDetailsEntity.setIfscCode(accountDetailsRequestDto.getIfscCode());
				accountManagementServiceRepository.save(accountDetailsEntity);
				apiResponseDto.setMessage("new account entry updated");
				apiResponseDto.setStatus(HttpStatus.OK);
			}

		} catch (Exception e) {
			log.info("Exception raised in addMoney() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto withdrawMoney(AccountDetailsRequestDto accountDetailsRequestDto) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			AccountDetailsEntity savedAccountDetailsEntity = accountManagementServiceRepository
					.findByCustomerId(accountDetailsRequestDto.getCustomerId());
			if (savedAccountDetailsEntity != null) {
				Double balance = savedAccountDetailsEntity.getBalance().doubleValue()
						- accountDetailsRequestDto.getBalance().doubleValue();
				savedAccountDetailsEntity.setBalance(balance);
				accountManagementServiceRepository.save(savedAccountDetailsEntity);
				apiResponseDto.setMessage("money debited");
				apiResponseDto.setStatus(HttpStatus.OK);
			} else {
				apiResponseDto.setMessage("account not available");
				apiResponseDto.setStatus(HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("Exception raised in withdrawMoney() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto getAccountDetails(Integer accountId) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		List<Object> arrayList = new ArrayList<>();
		try {
			Optional<AccountDetailsEntity> savedAccountDetailsEntity = accountManagementServiceRepository
					.findById(accountId);
			if (savedAccountDetailsEntity.isPresent()) {
				RestTemplate rt1 = new RestTemplate();
				Integer customerId = savedAccountDetailsEntity.get().getCustomerId();
				CustomerDetailsEntity customerDetailsEntity = rt1.getForObject(
						"http://localhost:8081/customer-management-service/get-customer-details/" + customerId,
						CustomerDetailsEntity.class);
				arrayList.add(customerDetailsEntity);
				arrayList.add(savedAccountDetailsEntity);
				apiResponseDto.setMessage("account details fetched successfully");
				apiResponseDto.setStatus(HttpStatus.OK);
				apiResponseDto.setData(arrayList);
			} else {
				apiResponseDto.setMessage("account not available");
				apiResponseDto.setStatus(HttpStatus.OK);
				apiResponseDto.setData("N/A");
			}
		} catch (Exception e) {
			log.info("Exception raised in getAccountDetails() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto deleteAccount(Integer accountId) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			accountManagementServiceRepository.deleteById(accountId);
			apiResponseDto.setMessage("account deleted successfully");
			apiResponseDto.setStatus(HttpStatus.OK);
		} catch (Exception e) {
			log.info("Exception raised in deleteAccount() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public AccountDetailsEntity getAccountDetailsByCustomerId(Integer customerId) {
		return accountManagementServiceRepository.findByCustomerId(customerId);
	}

}
