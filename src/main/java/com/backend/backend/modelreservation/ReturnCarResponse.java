package com.backend.backend.modelreservation;

import java.io.Serializable;

public class ReturnCarResponse implements Serializable {

    private int car_id;
    private double cost;

    public ReturnCarResponse(){}

    public ReturnCarResponse(int car_id, double cost) {
        this.car_id = car_id;
        this.cost = cost;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
