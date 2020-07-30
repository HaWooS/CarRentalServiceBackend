package com.backend.backend.modelreservation;

import java.io.Serializable;

public class ReservationRequest implements Serializable {
    private int id;
    private double deposit;
    private String username;
    private double fuel_cost;
    private int rent_days;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getFuel_cost() {
        return fuel_cost;
    }

    public void setFuel_cost(double fuel_cost) {
        this.fuel_cost = fuel_cost;
    }

    public int getRent_days() {
        return rent_days;
    }

    public void setRent_days(int rent_days) {
        this.rent_days = rent_days;
    }

    public ReservationRequest(int id, double deposit, String username, double fuel_cost, int rent_days) {
        this.id = id;
        this.deposit = deposit;
        this.username = username;
        this.fuel_cost = fuel_cost;
        this.rent_days = rent_days;
    }

    public ReservationRequest(){}
}
