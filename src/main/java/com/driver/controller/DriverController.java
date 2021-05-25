package com.driver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.driver.entities.Driver;
import com.driver.model.DriverRequest;
import com.driver.model.DriverResponse;
import com.driver.service.DriverService;

@RestController
public class DriverController {
    
	@Autowired
	private DriverService driverService;
	
	@GetMapping("/driver/{driverId}")
	public Driver getDriversById(@PathVariable(value = "driverId") String driverId){
		return driverService.getDriverById(driverId);
	}
	
	@GetMapping("/driver")
	public List<Driver> getAllDrivers(@RequestParam(name="transporterId",required=false) String transporterId,@RequestParam(name="phoneNum",required=false) String phoneNum,@RequestParam(name="truckId",required=false) String truckId
           ,@RequestParam(name="page",required=false) Optional<Integer> page ){
		return driverService.getAllDrivers(transporterId,phoneNum,truckId,page);
	}
	
	@PostMapping("/driver")
	public DriverResponse driverResponse(@RequestBody DriverRequest driverRequest) {
		return driverService.addDriver(driverRequest);
	}

	
	@PutMapping("/driver/{driverId}")
	public DriverResponse updateDriverResponse(@PathVariable(value = "driverId") String driverId,@RequestBody DriverRequest driverRequest) {
		
		return driverService.updateDriver(driverId,driverRequest);
		
	}

	
	@DeleteMapping("/driver/{driverId}")
	public  DriverResponse deleteDriver(@PathVariable(value = "driverId") String driverId) {
			return driverService.deleteDriver(driverId);
		
	}
	
	
}
