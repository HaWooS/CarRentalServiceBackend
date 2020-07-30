package com.backend.backend.modelstatistics;

import java.io.Serializable;

public class TripStatistics implements Serializable {
    public int car_id;
    public long quantity;

    public TripStatistics(int car_id, long quantity) {
        this.car_id = car_id;
        this.quantity = quantity;
    }

    public TripStatistics(){}
    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
