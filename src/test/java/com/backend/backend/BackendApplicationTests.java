package com.backend.backend;

import com.backend.backend.dao.UserRolesDAO;
import com.backend.backend.model.DAOUser;
import com.backend.backend.model.Role;
import com.backend.backend.model.UserDTO;
import com.backend.backend.model.UserRoles;
import com.backend.backend.modelarduino.ArduinoPositionRequest;
import com.backend.backend.modelcar.CarDTO;
import com.backend.backend.modelcar.PartialCarDTO;
import com.backend.backend.modelfuel.*;
import com.backend.backend.dao.*;
import com.backend.backend.modelcar.Car;
import com.backend.backend.service.ArduinoDetailsService;
import com.backend.backend.service.CarDetailsService;
import com.backend.backend.service.CyclicOperationsService;
import com.backend.backend.service.JwtUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	ArduinoDetailsService arduinoDetailsService;

	@Autowired
	CarDetailsService carDetailsService;

	@Autowired
	CyclicOperationsService cyclicOperationsService;

	@MockBean
	CarDAO carDAO;

	@MockBean
	FuelDAO fuelDAO;

	@MockBean
	UserDao userDao;

	@MockBean
	UserRolesDAO userRoles;

	@Test
	public void setFuelCostTest(){
		Fuel fuel = new Fuel(1,5.22);
		when(fuelDAO.findById(Mockito.anyInt())).thenReturn(fuel);
		when(fuelDAO.save(Mockito.any(Fuel.class))).thenReturn(fuel);
		assertEquals(fuel,carDetailsService.updateFuelCost(new FuelDTO(),1));
	}

	@Test
	public void getFuelCostTest() {
		int id=1;
		when(fuelDAO.findById(id)).thenReturn(
				new Fuel(1,5.22));
		assertEquals(java.util.Optional.of(5.22),java.util.Optional.of(carDetailsService.getFuelCost().getPrice()));
	}

	@Test
	public void saveCarTest(){
		CarDTO newCarRequest = new CarDTO(20,1111,"TK1111",500.0,50.62734,20.9348,false,false);
		Car newCar = new Car(20,1111,"TK1111",500.0,50.62734,20.9348,false,false);
		when(carDAO.save(Mockito.any(Car.class))).thenReturn(newCar);
		assertEquals(newCar,carDetailsService.saveCar(newCarRequest));
	}

	@Test
	public void addUserTest(){
		UserDTO newUser = new UserDTO("test1","ptest1","panTest","surTest","addrTest");
		DAOUser userRepresentation = new DAOUser("test1","ptest1","panTest","surTest","addrTest");
		when(userDao.findByUsername(Mockito.anyString())).thenReturn(null);
		when(userRoles.save(Mockito.any(UserRoles.class))).thenReturn(new UserRoles(5));
		when(userDao.save(Mockito.any(DAOUser.class))).thenReturn(userRepresentation);
		assertEquals(userRepresentation,jwtUserDetailsService.save(newUser));

	}

	@Test
	public void getCarsTest() {
		when(carDAO.findAll()).thenReturn(
				Stream.of(new Car(1,1111,"TK1111",500.0,50.62734,20.9348,false,false)).collect(Collectors.toList()));
		assertEquals(1,carDetailsService.getAllCars().size());
	}

	@Test
	public void getAvailableCarsTest(){
		Car expectedAvailableCar = new Car(2,2222,"TK2222",300.0,50.7238,20.89234,false,false);
		when(carDAO.findAvailableCars(false,false)).thenReturn(Collections.singletonList(expectedAvailableCar));
		assertEquals(Collections.singletonList(expectedAvailableCar),carDetailsService.getAvailableCars());
	}

	@Test
	public void updateCarTest(){
		Car expectedCarAfterUpdate = new Car(2,2222,"TK2222",300.0,50.7238,20.89234,false,false);
		PartialCarDTO partialCarDto = new PartialCarDTO("TK2222",300.0,50.999,20.99,false,false);
		when(carDAO.save(Mockito.any(Car.class))).thenReturn(expectedCarAfterUpdate);
		when(carDAO.findById(Mockito.anyInt())).thenReturn(expectedCarAfterUpdate);
		assertEquals(expectedCarAfterUpdate,carDetailsService.updateCar(partialCarDto,2));
	}

	@Test
	public void calculateCyclicDebtsTest(){
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				cyclicOperationsService.calculateCyclicDebts();
				LocalDateTime now = LocalDateTime.now();
				System.out.println("TESTOWE WYWOŁANIE CYCLIC DEBTS" + now);
			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		long delay  = 1000L;
		long period = 1000L;
		executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(delay + period * 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}
	@Test
	public void calculateImmediateDebtsTest(){
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				cyclicOperationsService.calculateImmediatelyOverdueDebts();
				LocalDateTime now = LocalDateTime.now();
				System.out.println("TESTOWE WYWOŁANIE IMMEDIATE DEBTS" + now);
			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		long delay  = 1000L;
		long period = 1000L;
		executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(delay + period * 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

	@Test
	public void calculateByCyclicOperationTest(){
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				cyclicOperationsService.checkRentsByCyclicOperation();
				LocalDateTime now = LocalDateTime.now();
				System.out.println("TESTOWE WYWOŁANIE BY CYCLIC OPERATION" + now);
			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		long delay  = 1000L;
		long period = 1000L;
		executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(delay + period * 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}


	@Test
	public void setCarLocationTest(){
		ArduinoPositionRequest request = new ArduinoPositionRequest(50.9999,20.9999);
		Car expectedCarWithNewLocation = new Car(1,1111,"TK1111",500.0,50.9999,20.9999,false,false);
		when(carDAO.findById(Mockito.anyInt())).thenReturn(expectedCarWithNewLocation);
		when(carDAO.save(Mockito.any(Car.class))).thenReturn(expectedCarWithNewLocation);
		assertEquals(expectedCarWithNewLocation,arduinoDetailsService.updatePosition(request));
	}

}
