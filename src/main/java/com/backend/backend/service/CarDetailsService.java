package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.FuelDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modelcar.CarDTO;
import com.backend.backend.modelcar.PartialCarDTO;
import com.backend.backend.modelfuel.Fuel;
import com.backend.backend.modelfuel.FuelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class CarDetailsService {
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private FuelDAO fuelDAO;


    public Car saveCar(CarDTO car) {
        Car newCar = new Car();
        newCar.setDeposit(car.getDeposit());
        newCar.setLatitude(car.getLatitude());
        newCar.setLongitude(car.getLongitude());
        newCar.setRegister_number(car.getRegister_number());
        newCar.setReservation(car.isReservation());
        newCar.setService(car.isService());
        newCar.setVin(car.getVin());
        return carDAO.save(newCar);
    }

    public List<Car> getAllCars()
    {
     return (List<Car>) carDAO.findAll();
    }

    public List<Car> getAvailableCars(){
        return (List<Car>) carDAO.findAvailableCars(false,false);
    }

    public Car updateCar(PartialCarDTO carDto, Integer id){

        Car old = carDAO.findById(id);
        old.setRegister_number(carDto.getRegister_number());
        old.setDeposit(carDto.getDeposit());
        old.setLatitude(carDto.getLatitude());
        old.setLongitude(carDto.getLongitude());
        old.setService(carDto.getService());
        old.setReservation(carDto.getReservation());
        return carDAO.save(old);
    }

    public ResponseEntity<Object> deleteCar(Integer id){
        Car toRemove = carDAO.findById(id);
        System.out.println("przepuscilo mnie");

        if(toRemove.equals(null)){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            carDAO.delete(toRemove);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> reserveCar(Integer id){
        Car toReserve = carDAO.findById(id);


        System.out.println("Samochod zostanie zarezerwowany "+id);
        if(toReserve.equals(null)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            toReserve.setReservation(true);
            carDAO.save(toReserve);
            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                Car undoReserve = carDAO.findById(id);
                undoReserve.setReservation(false);
                Car save = carDAO.save(undoReserve);
                return save;
            }, 1, TimeUnit.MINUTES);
            executorService.shutdown();

            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

    /*
    public ResponseEntity<Object> getFuelCost() {
        Fuel fuelCost = fuelDAO.findById(1);
        return new ResponseEntity<>(fuelCost, HttpStatus.OK);
    }
    */
    public Fuel getFuelCost() {
        Fuel fuelCost = fuelDAO.findById(1);
        return fuelCost;
    }

    public Fuel updateFuelCost(FuelDTO fuelDTO, Integer id){
        Fuel fuelToUpdate = fuelDAO.findById(id);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ZGETOWALEM ");
        System.out.println("COST Z DTO"+fuelDTO.getPrice());
        fuelToUpdate.setLast_modified(now);
        fuelToUpdate.setPrice(fuelDTO.getPrice());
        return fuelDAO.save(fuelToUpdate);
    }



}



