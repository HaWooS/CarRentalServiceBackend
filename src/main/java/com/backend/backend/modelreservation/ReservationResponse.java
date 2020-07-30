package com.backend.backend.modelreservation;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationResponse implements Serializable {
    private int car_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

    public ReservationResponse(){}
    public ReservationResponse(int car_id, LocalDateTime start_date, LocalDateTime end_date) {
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

    public LocalDateTime getStart_date() {
        return start_date;
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
}
