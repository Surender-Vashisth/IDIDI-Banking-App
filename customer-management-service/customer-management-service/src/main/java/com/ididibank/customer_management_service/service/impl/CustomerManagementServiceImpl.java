package com.ididibank.customer_management_service.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ididibank.customer_management_service.dto.AccountDetailsRequestDto;
import com.ididibank.customer_management_service.dto.ApiResponseDto;
import com.ididibank.customer_management_service.dto.CustomerDetailsRequestDto;
import com.ididibank.customer_management_service.entity.AccountDetailsEntity;
import com.ididibank.customer_management_service.entity.CustomerDetailsEntity;
import com.ididibank.customer_management_service.external.services.AccountManagementService;
import com.ididibank.customer_management_service.repository.CustomerManagementRepository;
import com.ididibank.customer_management_service.service.CustomerManagementService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerManagementServiceImpl implements CustomerManagementService {

	@Autowired
	private CustomerManagementRepository customerManagementRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AccountManagementService accountManagementService;

	@Override
	@Transactional
	public ApiResponseDto addCustomer(CustomerDetailsRequestDto customerDetailsDto) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			CustomerDetailsEntity customerDetailsEntity = modelMapper.map(customerDetailsDto,
					CustomerDetailsEntity.class);
			CustomerDetailsEntity customerDetailsEntity2 = customerManagementRepository.save(customerDetailsEntity);
			AccountDetailsRequestDto accountDetailsRequestDto = new AccountDetailsRequestDto();
			Long nextLong = ThreadLocalRandom.current().nextLong();
			accountDetailsRequestDto.setAccountNumber(Long.parseLong(nextLong.toString().substring(5)));
			accountDetailsRequestDto.setBalance(1000.00);
			accountDetailsRequestDto.setBranch("New Delhi");
			accountDetailsRequestDto.setCustomerId(customerDetailsEntity2.getCustomerId());
			accountDetailsRequestDto.setIfscCode("IDIDI000ND");
			accountManagementService.addMoney(accountDetailsRequestDto);
			apiResponseDto.setMessage("customer added");
			apiResponseDto.setStatus(HttpStatus.OK);
		} catch (Exception e) {
			apiResponseDto.setMessage("Exception Raised in addCustomer() : " + e.getMessage());
			apiResponseDto.setStatus(HttpStatus.FORBIDDEN);
			log.info("Exception Raised in addCustomer() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto updateCustomer(CustomerDetailsRequestDto customerDetailsDto) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			CustomerDetailsEntity customerDetailsEntity = modelMapper.map(customerDetailsDto,
					CustomerDetailsEntity.class);
			Optional<CustomerDetailsEntity> findById = customerManagementRepository
					.findById(customerDetailsEntity.getCustomerId());
			if (findById.isPresent()) {
				customerManagementRepository.save(customerDetailsEntity);
				apiResponseDto.setMessage("customer updated");
				apiResponseDto.setStatus(HttpStatus.OK);
			} else {
				apiResponseDto.setMessage("customer not exist");
				apiResponseDto.setStatus(HttpStatus.OK);
			}
		} catch (Exception e) {
			apiResponseDto.setMessage("Exception Raised in updateCustomer() : " + e.getMessage());
			apiResponseDto.setStatus(HttpStatus.FORBIDDEN);
			log.info("Exception Raised in updateCustomer() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto getAllCustomers() {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			List<CustomerDetailsEntity> allCustomers = customerManagementRepository.findAll();
			apiResponseDto.setMessage("all customers fetched successfully");
			apiResponseDto.setStatus(HttpStatus.OK);
			apiResponseDto.setData(allCustomers);
		} catch (Exception e) {
			apiResponseDto.setMessage("Exception Raised in getAllCustomers() : " + e.getMessage());
			apiResponseDto.setStatus(HttpStatus.FORBIDDEN);
			log.info("Exception Raised in getAllCustomers() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	public ApiResponseDto getCustomerDetails(Integer customerId) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			Optional<CustomerDetailsEntity> findById = customerManagementRepository.findById(customerId);
			if (findById.isPresent()) {
				apiResponseDto.setMessage("customer details fetched successfully");
				apiResponseDto.setStatus(HttpStatus.OK);
				apiResponseDto.setData(findById.get());
			} else {
				apiResponseDto.setMessage("customer does not exist");
				apiResponseDto.setStatus(HttpStatus.OK);
				apiResponseDto.setData("N/A");
			}
		} catch (Exception e) {
			apiResponseDto.setMessage("Exception Raised in getCustomerDetails() : " + e.getMessage());
			apiResponseDto.setStatus(HttpStatus.FORBIDDEN);
			log.info("Exception Raised in getCustomerDetails() : " + e.getMessage());
		}
		return apiResponseDto;
	}

	@Override
	@Transactional
	public ApiResponseDto deleteCustomer(Integer customerId) {
		ApiResponseDto apiResponseDto = new ApiResponseDto();
		try {
			RestTemplate rt1 = new RestTemplate();
			AccountDetailsEntity accountDetailsEntity = rt1.getForObject(
					"http://localhost:8082/account-management-service/get-account-details-by-customerId/" + customerId,
					AccountDetailsEntity.class);
			Optional<CustomerDetailsEntity> customerDetailsEntity = customerManagementRepository.findById(customerId);
			if (customerDetailsEntity.isPresent() && accountDetailsEntity != null) {
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.delete("http://localhost:8082/account-management-service/delete-account/"
						+ accountDetailsEntity.getAccountId());
				customerManagementRepository.deleteById(customerId);
				apiResponseDto.setMessage("customer deleted successfully");
				apiResponseDto.setStatus(HttpStatus.OK);

			} else {
				apiResponseDto.setMessage("customer does not exist");
				apiResponseDto.setStatus(HttpStatus.OK);
			}
		} catch (Exception e) {
			apiResponseDto.setMessage("Exception Raised in deleteCustomer() : " + e.getMessage());
			apiResponseDto.setStatus(HttpStatus.FORBIDDEN);
			log.info("Exception Raised in deleteCustomer() : " + e.getMessage());
		}
		return apiResponseDto;
	}
}
