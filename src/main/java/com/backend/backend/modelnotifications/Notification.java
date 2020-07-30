package com.backend.backend.modelnotifications;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="Notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    private int car_id;


    public Notification(){}

    public Notification(String username, LocalDateTime start_date, LocalDateTime end_date, int car_id) {
        this.username = username;
        this.start_date = start_date;
        this.end_date = end_date;
        this.car_id = car_id;
    }
    public Notification(int id,String username, LocalDateTime start_date, LocalDateTime end_date, int car_id) {
        this.id=id;
        this.username = username;
        this.start_date = start_date;
        this.end_date = end_date;
        this.car_id = car_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

}
