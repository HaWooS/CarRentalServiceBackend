package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modeldepositpayment.DepositPaidRequest;
import com.backend.backend.modeldepositpayment.DepositPaidResponse;
import com.backend.backend.modelrent.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DepositDetailsService {
    @Autowired
    private RentDAO rentDAO;

    @Autowired
    private DataParser dataParser;

    @Autowired
    private CarDAO carDAO;

    public int generate_code(){
        Random r = new Random();
        int boxCode = 1000 + r.nextInt(9000);
        return boxCode;
    }

    public DepositPaidResponse payDeposit(DepositPaidRequest depositPaidRequest, Authentication authentication)
    {
        DataParser parser = new DataParser();
        String username = authentication.getName();
        Car carToCheck = carDAO.findById(depositPaidRequest.getId());
        //Rent checkRent = rentDAO.findRentToPayDeposit(1,"user2",parser.parseSingleDateFromString(depositPaidRequest.getStart_date()),false);
        Rent checkRent = rentDAO.findRentToPayDeposit(depositPaidRequest.getId(),username,parser.parseSingleDateFromString(depositPaidRequest.getStart_date()),false);
        System.out.println("DEPOZYT PRZED IFEM "+checkRent.isDeposit_paid());
        //tutaj cos dodalem nowego
        if(carToCheck.isReservation()==true) {
            if (checkRent.isDeposit_paid() == false) {
                int boxCode = generate_code();
                System.out.println(checkRent.getCar_id());
                System.out.println(checkRent.isDeposit_paid());
                System.out.println(checkRent.getEnd_date());
                System.out.println(checkRent.getStart_date());
                System.out.println(checkRent.getBox_code());
                System.out.println(checkRent.getDeposit());
                System.out.println(checkRent.getFuel_cost());
                System.out.println(checkRent.getUsername());
                checkRent.setDeposit_paid(true);
                carToCheck.setService(true);
                System.out.println("PO USTAWIENIU " + checkRent.isDeposit_paid());
                checkRent.setBox_code(boxCode);
                rentDAO.save(checkRent);
                //int boxCode = generate_code();
                System.out.println("wygenerowany kod do skrzynki " + boxCode);
                DepositPaidResponse depositPaidResponse = new DepositPaidResponse(boxCode);
                return depositPaidResponse;
            } else {
                int boxCode = checkRent.getBox_code();
                DepositPaidResponse depositPaidResponse = new DepositPaidResponse(boxCode);
                return depositPaidResponse;
            }
        }else
        {
            return null;
        }

    }



}
