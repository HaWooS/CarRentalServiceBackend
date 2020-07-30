package com.backend.backend.modelreservation;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReturnCarRequest implements Serializable {
    private int car_id;
    private String start_date;
    private String end_date;

    public ReturnCarRequest(){}

    public ReturnCarRequest(int car_id, String start_date, String end_date) {
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}

