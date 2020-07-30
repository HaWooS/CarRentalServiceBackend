package com.backend.backend;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.DebtorCheckerDAO;
import com.backend.backend.dao.FuelDAO;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modeldebtorchecker.DebtorChecker;
import com.backend.backend.modelfuel.Fuel;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.service.CarReturnDetailsService;
import com.backend.backend.service.CyclicOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@SpringBootApplication
public class BackendApplication {
	@Autowired
	public CarReturnDetailsService carReturnDetailsService;

	@Autowired
	public CyclicOperationsService cyclicOperationsService;

	@Autowired
	public DebtorCheckerDAO debtorCheckerDAO;

	@Autowired
	public FuelDAO fuelDAO;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

	}


	@Bean
	public WebMvcConfigurer configure(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}

	@Bean
	CommandLineRunner mapping(CarDAO carDAO, RentDAO rentDAO, FuelDAO fuelDAO){
		return args-> {
		    /*
			Car car = new Car(58222, "TK2332", 500.3, 50.875883, 20.634377, false, false);
			carDAO.save(car);
			Car car2 = new Car(99222, "TK333", 454.3, 50.8757883, 20.638377, false, false);
			carDAO.save(car2);
			Car car3 = new Car(45222, "TK2444", 878.2, 50.8758883, 20.639377, false, false);
			carDAO.save(car3);
			*/
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime parsedStartDate = LocalDateTime.parse("2020-01-02 10:33:55", formatter);
			LocalDateTime parsedEndDate = LocalDateTime.parse("2020-01-02 12:33:23", formatter);
			/*Rent rent = new Rent(2,"test1", parsedStartDate, parsedEndDate,true,2322,300,5.22,true,0);
			rentDAO.save(rent);
			Rent rent1 = new Rent(2,"test1", parsedStartDate, parsedEndDate,true,2322,300,5.22,true,0);
			rentDAO.save(rent1);
			Rent rent3 = new Rent(3,"test1", parsedStartDate, parsedEndDate,true,2322,300,5.22,true,0);
			rentDAO.save(rent3);
			Rent rent4 = new Rent(4,"test1", parsedStartDate, parsedEndDate,true,2322,300,5.22,true,0);
			rentDAO.save(rent4);
			Rent rent5 = new Rent(5,"test3", parsedStartDate, parsedEndDate,true,9999,530,5.22,true,0);
			rentDAO.save(rent5);
			*/
			/*
			Fuel fuel1 = new Fuel(5.22,parsedEndDate);
			fuelDAO.save(fuel1);
			Rent rent1 = new Rent(1,"user1", LocalDateTime.parse("2020-01-02 10:33:55", formatter), LocalDateTime.parse("2020-01-20 10:33:55", formatter),false,2322,300,5.22,true,1111);
			rentDAO.save(rent1);
			Rent rent2 = new Rent(2,"user1", LocalDateTime.parse("2020-01-02 10:33:55", formatter), LocalDateTime.parse("2020-01-20 10:33:55", formatter),false,0,300,5.22,true,2222);
			rentDAO.save(rent2);
			Rent rent3 = new Rent(3,"user1", LocalDateTime.parse("2020-01-02 10:33:55", formatter), LocalDateTime.parse("2020-02-29 10:33:55", formatter),false,0,300,5.22,true,8522);
			rentDAO.save(rent3);
			Rent rent4 = new Rent(1,"user2", LocalDateTime.parse("2020-01-02 10:33:55", formatter), LocalDateTime.parse("2020-02-07 10:33:55", formatter),false,0,300,5.22,true,8522);
			rentDAO.save(rent4);
            */

			//DebtorChecker debtorChecker = new DebtorChecker(1,parsedEndDate);
			//debtorCheckerDAO.save(debtorChecker);
			cyclicOperationsService.runCyclicOperations();

			/*

			final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.schedule(() -> {
				LocalDateTime now = LocalDateTime.now();
				System.out.println("TERAZ MAMY : " + now);
				carReturnDetailsService.returnCarByCyclicOperation(now);
			}, 1, TimeUnit.MINUTES);
			//rentDAO.save(new Rent(int time, Time date, Double cost, String username,Car car));
*/
		};
	}




}
