package com.driver.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.driver.Exception.EntityNotFoundException;
import com.driver.constantMessages.Constants;
import com.driver.dao.DriverRepository;
import com.driver.entities.Driver;
import com.driver.model.DriverRequest;
import com.driver.model.DriverResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Driver getDriverById(String driverId) {

		Optional<Driver> d = (driverRepository.findById(driverId));

		if (d.isEmpty()) {
			EntityNotFoundException ex = new EntityNotFoundException(Driver.class, "driverId", driverId.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		log.info("Driver Data returned");
		return d.get();

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DriverResponse addDriver(DriverRequest driverRequest) {
		DriverResponse driverResponse = new DriverResponse();
		Driver d = new Driver();

		String driverid = "driver:" + UUID.randomUUID();

		d.setDriverId(driverid);
		d.setTransporterId(driverRequest.getTransporterId());
		d.setPhoneNum(driverRequest.getPhoneNum());
		d.setDriverName(driverRequest.getDriverName());

		if (driverRequest.getTruckId() != null) {
			d.setTruckId(driverRequest.getTruckId());
		}
		driverRepository.save(d);
		log.info("Driver Data is saved");
		driverResponse.setStatus(Constants.driverAdded);
		driverResponse.setDriverName(d.getDriverName());
		driverResponse.setPhoneNum(d.getPhoneNum());
		driverResponse.setTransporterId(d.getTransporterId());
		driverResponse.setTruckId(d.getTruckId());

		log.info("Post Service Response returned");
		return driverResponse;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DriverResponse updateDriver(String driverId, DriverRequest driverRequest) {
		DriverResponse driverResponse = new DriverResponse();

		Driver d = driverRepository.findById(driverId).orElse(null);

		if (d == null) {
			EntityNotFoundException ex = new EntityNotFoundException(Driver.class, "driverId", driverId.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		if (driverRequest.getDriverName() != null) {
			d.setDriverName(driverRequest.getDriverName());
		}
		if (driverRequest.getTruckId() != null) {
			d.setTruckId(driverRequest.getTruckId());
		}

		driverRepository.save(d);
		log.info("Driver Data is updated");
		driverResponse.setStatus(Constants.updateSuccess);
		driverResponse.setDriverName(d.getDriverName());
		driverResponse.setPhoneNum(d.getPhoneNum());
		driverResponse.setTransporterId(d.getTransporterId());
		driverResponse.setTruckId(d.getTruckId());
		log.info("Put Service Response returned");
		return driverResponse;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DriverResponse deleteDriver(String driverId) {

		DriverResponse driverResponse = new DriverResponse();

		Driver d = driverRepository.findById(driverId).orElse(null);

		if (d == null) {
			EntityNotFoundException ex = new EntityNotFoundException(Driver.class, "driverId", driverId.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		driverRepository.delete(d);
		log.info("Deleted");
		driverResponse.setStatus(Constants.deleteSuccess);
		log.info("Deleted Service Response returned");
		return driverResponse;

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Driver> getAllDrivers(String transporterId, String phoneNum, String truckId,
			@RequestParam Optional<Integer> page) {

		if (transporterId != null && phoneNum == null && truckId == null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByTransporterId(transporterId,
					PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (phoneNum != null && truckId == null && transporterId == null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByPhoneNum(phoneNum, PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (truckId != null && transporterId == null && phoneNum == null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByTruckId(truckId, PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (transporterId != null && phoneNum != null && truckId == null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByPhoneNumAndTransporterId(phoneNum, transporterId,
					PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (transporterId != null && phoneNum == null && truckId != null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByTruckIdAndTransporterId(truckId, transporterId,
					PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (transporterId == null && phoneNum != null && truckId != null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByPhoneNumAndTruckId(phoneNum, truckId,
					PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		if (transporterId != null && phoneNum != null && truckId != null) {
			log.info("Driver Data with params returned");
			return driverRepository.findByPhoneNumAndTransporterIdAndTruckId(phoneNum, transporterId,truckId,
					PageRequest.of(page.orElse(0), (int) Constants.pageSize));
		}

		log.info("Driver Data get all method called");
		return driverRepository.findAllDrivers(PageRequest.of(page.orElse(0), (int) Constants.pageSize));

	}

}
