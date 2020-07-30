package com.backend.backend.dao;

import com.backend.backend.modeldebtor.Debtor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface DebtorDAO extends CrudRepository<Debtor,Long> {
    Debtor findById(Integer id);
    @Query(value = "select new com.backend.backend.modeldebtor.Debtor(debtor.id,debtor.car_id,debtor.username,debtor.start_date,debtor.end_date,debtor.rent_paid,debtor.rent_cost,debtor.deposit,debtor.fuel_cost,debtor.deposit_paid,debtor.box_code) from Debtor debtor where debtor.car_id=?1  and debtor.username=?2 and debtor.start_date=?3 and debtor.end_date=?4")
    Debtor findSingleDebtor(int car_id, String username, LocalDateTime start_date, LocalDateTime end_date);

    Debtor findByUsername(String username);
}
