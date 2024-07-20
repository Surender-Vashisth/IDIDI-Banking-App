package com.ididibank.customer_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ididibank.customer_management_service.entity.CustomerDetailsEntity;

@Repository
public interface CustomerManagementRepository extends JpaRepository<CustomerDetailsEntity, Integer>{
	
}
