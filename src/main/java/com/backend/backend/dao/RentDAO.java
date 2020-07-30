package com.backend.backend.dao;

import com.backend.backend.modelrent.Rent;
import com.backend.backend.modelrent.RentFromDatabase;
import com.backend.backend.modelstatistics.EarningStatistics;
import com.backend.backend.modelstatistics.TripStatistics;
import org.apache.tomcat.jni.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RentDAO extends CrudRepository<Rent,Long>{
    @Query(value = "select new com.backend.backend.modelrent.Rent(rent.id,rent.car_id,rent.username,rent.start_date,rent.end_date,rent.rent_paid,rent.rent_cost,rent.deposit,rent.fuel_cost,rent.deposit_paid,rent.box_code) from Rent rent where rent.car_id=?1 and rent.username=?2 and rent.start_date=?3")
    Rent findByCredentials(int car_id, String username, LocalDateTime start_date);

    @Query(value = "select new com.backend.backend.modelrent.Rent(rent.id,rent.car_id,rent.username,rent.start_date,rent.end_date,rent.rent_paid,rent.rent_cost,rent.deposit,rent.fuel_cost,rent.deposit_paid,rent.box_code) from Rent rent where rent.car_id=?1 and rent.username=?2 and rent.start_date=?3 and rent.end_date=?4")
    Rent findSingleRent(int car_id, String username, LocalDateTime start_date, LocalDateTime end_date);

    @Query(value = "select new com.backend.backend.modelrent.Rent(rent.id,rent.car_id,rent.username,rent.start_date,rent.end_date,rent.rent_paid,rent.rent_cost,rent.deposit,rent.fuel_cost,rent.deposit_paid,rent.box_code) from Rent rent where rent.car_id=?1 and rent.username=?2 and rent.start_date=?3 and rent.deposit_paid=?4")
    Rent findRentToPayDeposit(int car_id, String username, LocalDateTime start_date, boolean deposit_paid);

    @Query(value = "select new com.backend.backend.modelrent.Rent(rent.id,rent.car_id,rent.username,rent.start_date,rent.end_date,rent.rent_paid,rent.rent_cost,rent.deposit,rent.fuel_cost,rent.deposit_paid,rent.box_code) from Rent rent where rent.rent_paid=?2 and rent.end_date<?1")
    List<Rent> getAllRents(LocalDateTime now,boolean conditional);

    @Query(value = "select new com.backend.backend.modelstatistics.EarningStatistics(rent.car_id, sum(rent.rent_cost)) from Rent rent where rent.start_date between ?1 and ?2 group by rent.car_id")
    List<EarningStatistics> getStatistics(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select new com.backend.backend.modelrent.Rent(rent.id,rent.car_id,rent.username,rent.start_date,rent.end_date,rent.rent_paid,rent.rent_cost,rent.deposit,rent.fuel_cost,rent.deposit_paid,rent.box_code) from Rent rent where rent.rent_paid=?1 and rent.username=?2")
    Rent findByUsernameAndAndRentpaid(boolean rent_paid, String username);

    @Query("select new com.backend.backend.modelstatistics.TripStatistics(rent.car_id, count(rent.car_id)) from Rent rent where rent.start_date between ?1 and ?2 group by rent.car_id")
    List<TripStatistics> getTrips(LocalDateTime startDate, LocalDateTime endDate);
}

