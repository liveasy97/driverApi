package com.driver.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DriverController {

	@Autowired
	private DriverService driverService;

	@GetMapping("/driver/{driverId}")
	public  ResponseEntity<Driver> getDriversById(@PathVariable(value = "driverId") String driverId) {
		log.info("Get Controller Started");
		return new ResponseEntity<>(driverService.getDriverById(driverId),
				HttpStatus.OK);
	}

	@GetMapping("/driver")
	public  ResponseEntity<List<Driver>> getAllDrivers(@RequestParam(name = "transporterId", required = false) String transporterId,
			@RequestParam(name = "phoneNum", required = false) String phoneNum,
			@RequestParam(name = "truckId", required = false) String truckId,
			@RequestParam(name = "page", required = false) Optional<Integer> page) {
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(driverService.getAllDrivers(transporterId, phoneNum, truckId, page),
				HttpStatus.OK);
	}

	@PostMapping("/driver")
	public  ResponseEntity<DriverResponse> driverResponse(@Valid @RequestBody DriverRequest driverRequest) {
		log.info("Post Controller Started");
		return new ResponseEntity<>(driverService.addDriver(driverRequest),
				HttpStatus.CREATED);
	}

	@PutMapping("/driver/{driverId}")
	public  ResponseEntity<DriverResponse> updateDriverResponse(@Valid @PathVariable(value = "driverId") String driverId,
			@RequestBody DriverRequest driverRequest) {
		log.info("Put Controller Started");
		return new ResponseEntity<>(driverService.updateDriver(driverId, driverRequest),
				HttpStatus.OK);

	}

	@DeleteMapping("/driver/{driverId}")
	public  ResponseEntity<DriverResponse> deleteDriver(@PathVariable(value = "driverId") String driverId) {
		log.info("Delete Controller Started");
		return new ResponseEntity<>(driverService.deleteDriver(driverId),
				HttpStatus.OK);

	}

}
