package com.backend.backend.modelstatistics;

import java.io.Serializable;

public class EarningStatistics implements Serializable {
    public int car;
    public double cost;

    public EarningStatistics(int car, double cost) {
        this.car = car;
        this.cost = cost;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
