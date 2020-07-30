package com.backend.backend.modepayment;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    private int car_id;
    private String status;

    public PaymentResponse(){}
    public PaymentResponse(int car_id, String status) {
        this.car_id = car_id;
        this.status = status;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
