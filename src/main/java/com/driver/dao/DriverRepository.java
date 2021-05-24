package com.driver.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.driver.entities.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

	
	
	@Query("select d from Driver d where d.transporterId = :transporterId")
	List<Driver> findByTransportId( String transporterId,Pageable pageable);

	@Query("select d from Driver d where d.phoneNum = :phoneNum")
	List<Driver> findByPhoneNum(String phoneNum,Pageable pageable);

	@Query("select d from Driver d where d.truckId = :truckId")
	List<Driver> findByTruckId(String truckId,Pageable pageable);
	
	@Query("select d from Driver d")
	List<Driver> findAllDrivers(Pageable pageable);

	
	
	
}
