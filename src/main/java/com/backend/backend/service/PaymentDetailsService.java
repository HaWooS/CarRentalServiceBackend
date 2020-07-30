package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.DebtorDAO;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modeldebtor.Debtor;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.modepayment.PaymentRequest;
import com.backend.backend.modepayment.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsService {
    @Autowired
    private RentDAO rentDAO;
    @Autowired
    private DataParser dataParser;
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private DebtorDAO debtorDAO;


    public PaymentResponse confirmPayment(PaymentRequest paymentRequest)
    {
        Rent rentToCheck = rentDAO.findSingleRent(paymentRequest.getId(),paymentRequest.getUsername(),dataParser.parseSingleDateFromString(paymentRequest.getStart_date()),dataParser.parseSingleDateFromString(paymentRequest.getEnd_date()));
        if(rentToCheck==null){
            //nie ma go w rents wiec jest dluznikiem
            Debtor debtorToDelete = debtorDAO.findSingleDebtor(paymentRequest.getId(),paymentRequest.getUsername(),dataParser.parseSingleDateFromString(paymentRequest.getStart_date()),dataParser.parseSingleDateFromString(paymentRequest.getEnd_date()));
            Rent paydRent = new Rent(debtorToDelete.getCar_id(),
                    debtorToDelete.getUsername(),
                    debtorToDelete.getStart_date(),
                    debtorToDelete.getEnd_date(),
                    true,
                    debtorToDelete.getRent_cost(),
                    debtorToDelete.getDeposit(),
                    debtorToDelete.getFuel_cost(),
                    debtorToDelete.isDeposit_paid(),
                    debtorToDelete.getBox_code()
                    );
            rentDAO.save(paydRent);
            debtorDAO.delete(debtorToDelete);
            return new PaymentResponse(paymentRequest.getId(),"success");
        }else{
            //jest w rentach wiec update rent
            rentToCheck.setRent_paid(true);
            rentDAO.save(rentToCheck);
            return new PaymentResponse(paymentRequest.getId(),"success");
        }
    }



}
