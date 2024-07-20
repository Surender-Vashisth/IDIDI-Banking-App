package com.ididibank.account_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ididibank.account_management_service.entity.AccountDetailsEntity;

@Repository
public interface AccountManagementServiceRepository extends JpaRepository<AccountDetailsEntity, Integer>{

	AccountDetailsEntity findByCustomerId(Integer customerId);

}
