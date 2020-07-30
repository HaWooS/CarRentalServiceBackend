package com.backend.backend.service;

import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.modelrent.RentDTO;
import com.backend.backend.modelrent.RentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class StatisticsDetailsService {
/*
    @Autowired
    private RentDAO rentDAO;

    //@Query(value = "SELECT r.id, SUM(r.cost) from Rent r where r.date BETWEEN ?1 AND ?2",nativeQuery=true)
    public List<Rent> earningStatistics(java.sql.Time startDate, java.sql.Time endDate){
        System.out.println(rentDAO.getStatistics(startDate,endDate));
        return (List<Rent>) rentDAO.getStatistics(startDate,endDate);
    }*/
}
