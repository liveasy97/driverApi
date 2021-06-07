package com.driver.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "driver")
@Data
public class Driver {

	@Id
	private String driverId;
	
	private String transporterId;
	private String phoneNum;
	private String driverName;
	private String truckId;
	
}
