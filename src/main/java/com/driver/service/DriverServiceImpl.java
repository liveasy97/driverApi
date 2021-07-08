package com.driver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.driver.constantMessages.Constants;
import com.driver.dao.DriverRepository;
import com.driver.entities.Driver;
import com.driver.model.DriverRequest;
import com.driver.model.DriverResponse;


@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository ;
	
	
	@Override
	public Driver getDriverById(String driverId) {
		Optional<Driver> d = driverRepository.findById(driverId);
		if(d.isEmpty()) {
			return null;
		}
		return d.get();	
		
	}

	@Override
	public DriverResponse addDriver(DriverRequest driverRequest) {
		DriverResponse driverResponse = new DriverResponse();
		Driver d = new Driver();
		
		String regex = "^[6-9]\\d{9}$";
		Pattern pattern = Pattern.compile(regex);		
		
		List<String> listOfDriver = new ArrayList<>();
		
		if(driverRequest.getTransporterId()==null) {
			driverResponse.setStatus(Constants.transporterIdError);
			return driverResponse;
		}
		
		if(driverRequest.getDriverName()==null) {
			driverResponse.setStatus(Constants.driverNameError);
			return driverResponse;
		}
		
		if(driverRequest.getPhoneNum()==null ) {
			driverResponse.setStatus(Constants.phoneNumError1);
			return driverResponse;
		}else {
			Matcher matcher = pattern.matcher(driverRequest.getPhoneNum());
			if(!matcher.matches()) {
				driverResponse.setStatus(Constants.phoneNumError2);
				return driverResponse;
			}
				
		}
		
		
		listOfDriver=driverRepository.findPhoneNumByTransportId(driverRequest.getTransporterId());  
		
		if(listOfDriver.contains((String.valueOf(driverRequest.getPhoneNum())))){
			driverResponse.setStatus(Constants.phoneNumError3);
			return driverResponse;
		}
		
		
		String driverid="driver:"+UUID.randomUUID();
		
		d.setDriverId(driverid);
		d.setTransporterId(driverRequest.getTransporterId());
		d.setPhoneNum(driverRequest.getPhoneNum());
		d.setDriverName(driverRequest.getDriverName());
	
		if(driverRequest.getTruckId()!=null) {
			d.setTruckId(driverRequest.getTruckId());
		}
		
		driverResponse.setStatus(Constants.driverAdded);
		driverRepository.save(d);	
		return driverResponse;
		
		}
	

	@Override
	public DriverResponse updateDriver(String driverId, DriverRequest driverRequest) {
		DriverResponse driverResponse = new DriverResponse();
		Driver d = new Driver();
		
		Optional<Driver> driver = driverRepository.findById(driverId);
		if(driver.isPresent()) {
			d = driver.get();
		}
		else {
			driverResponse.setStatus(Constants.AccountNotFoundError);
			return driverResponse;
		}
		
		
		
		if(driverRequest.getDriverName()!=null) {
			d.setDriverName(driverRequest.getDriverName());
		}
		if(driverRequest.getTruckId()!=null) {
			d.setTruckId(driverRequest.getTruckId());
		}
		
		driverRepository.save(d);	
		driverResponse.setStatus(Constants.updateSuccess);
		
		return driverResponse;
		}
	

	@Override
	public DriverResponse deleteDriver(String driverId) {
		
		DriverResponse driverResponse = new DriverResponse();
		Optional<Driver> d = driverRepository.findById(driverId);
		if(d.isEmpty()) {
			driverResponse.setStatus(Constants.AccountNotFoundError);
			return driverResponse;
		}
		
		driverRepository.delete(d.get());
		driverResponse.setStatus(Constants.deleteSuccess);
		
		return driverResponse;
		
		}

	
	
	@Override
	public List<Driver> getAllDrivers(String transporterId,String phoneNum,String truckId,@RequestParam Optional<Integer> page) {

		int limit=5;
	
		if(transporterId!=null) {
			return driverRepository.findByTransportId(transporterId, PageRequest.of(
	                page.orElse(0),
	                limit
	                ));  
		}

		if(phoneNum!=null) {
			return driverRepository.findByPhoneNum(phoneNum, PageRequest.of(
	                page.orElse(0),
	                limit
	                )); 
		}

		if(truckId!=null) {
			return driverRepository.findByTruckId(truckId, PageRequest.of(
	                page.orElse(0),
	                limit
	                )); 
		}

		return  driverRepository.findAllDrivers(PageRequest.of(
	                page.orElse(0),
	                limit
	                )); 
	
	}

}


