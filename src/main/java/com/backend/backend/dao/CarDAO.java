package com.backend.backend.dao;

import com.backend.backend.modelcar.Car;
import com.backend.backend.modelrent.Rent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CarDAO extends CrudRepository<Car,Long> {
    Car findById(Integer id);

    @Query(value = "select new com.backend.backend.modelcar.CarDTO(car.id, car.vin, car.register_number, car.deposit, car.latitude, car.longitude, car.service, car.reservation)" +
            " from Car car where car.service=?1 and car.reservation=?2")
    List <Car> findAvailableCars(boolean service, boolean reservation);

}
