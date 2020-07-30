package com.backend.backend.modelrent;

import com.backend.backend.modelcar.Car;

import javax.persistence.*;

import java.io.Serializable;
//import java.sql.Date;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="Rent")
public class Rent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int car_id;

    private String username;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    private boolean rent_paid;

    private double rent_cost;

    private double deposit;

    private double fuel_cost;

    private boolean deposit_paid;

    private int box_code;


    public Rent(){}


    public Rent(int carId, String username, LocalDateTime start_date, LocalDateTime end_date, boolean rent_paid, double rent_cost, double deposit, double fuel_cost, boolean deposit_paid, int box_code) {
        this.car_id = carId;
        this.username = username;
        this.start_date = start_date;
        this.end_date = end_date;
        this.rent_paid = rent_paid;
        this.rent_cost = rent_cost;
        this.deposit = deposit;
        this.fuel_cost = fuel_cost;
        this.deposit_paid = deposit_paid;
        this.box_code=box_code;
    }
    public Rent(Long id,int carId, String username, LocalDateTime start_date, LocalDateTime end_date, boolean rent_paid, double rent_cost, double deposit, double fuel_cost, boolean deposit_paid, int box_code) {
        this.id=id;
        this.car_id = carId;
        this.username = username;
        this.start_date = start_date;
        this.end_date = end_date;
        this.rent_paid = rent_paid;
        this.rent_cost = rent_cost;
        this.deposit = deposit;
        this.fuel_cost = fuel_cost;
        this.deposit_paid = deposit_paid;
        this.box_code=box_code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public int getBox_code() {
        return box_code;
    }

    public void setBox_code(int box_code) {
        this.box_code = box_code;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public boolean isRent_paid() {
        return rent_paid;
    }

    public void setRent_paid(boolean rent_paid) {
        this.rent_paid = rent_paid;
    }

    public double getRent_cost() {
        return rent_cost;
    }

    public void setRent_cost(double rent_cost) {
        this.rent_cost = rent_cost;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getFuel_cost() {
        return fuel_cost;
    }

    public void setFuel_cost(double fuel_cost) {
        this.fuel_cost = fuel_cost;
    }

    public boolean isDeposit_paid() {
        return deposit_paid;
    }

    public void setDeposit_paid(boolean deposit_paid) {
        this.deposit_paid = deposit_paid;
    }
}
