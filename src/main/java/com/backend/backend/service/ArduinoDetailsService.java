package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.modelarduino.ArduinoPositionRequest;
import com.backend.backend.modelcar.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class ArduinoDetailsService {
    @Autowired
    CarDAO carDao;

    public Car updatePosition(ArduinoPositionRequest arduinoPositionRequest){
        System.out.println("Update pozycji");
        Car carToUpdatePosition = carDao.findById(1);
        carToUpdatePosition.setLatitude(arduinoPositionRequest.getLatitude());
        carToUpdatePosition.setLongitude(arduinoPositionRequest.getLongitude());
        return carDao.save(carToUpdatePosition);
    }
}
