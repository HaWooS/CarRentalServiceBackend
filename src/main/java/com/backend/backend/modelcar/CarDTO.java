package com.backend.backend.modelcar;

public class CarDTO {
    private int id;

    private int vin;

    private String register_number;

    private Double deposit; //koszt kaucji

    private Double latitude;

    private Double longitude;

    private boolean service;

    private boolean reservation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getRegister_number() {
        return register_number;
    }

    public void setRegister_number(String register_number) {
        this.register_number = register_number;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    public CarDTO(int id, int vin, String register_number, Double deposit, Double latitude, Double longitude, boolean service, boolean reservation) {
        this.id = id;
        this.vin = vin;
        this.register_number = register_number;
        this.deposit = deposit;
        this.latitude = latitude;
        this.longitude = longitude;
        this.service = service;
        this.reservation = reservation;
    }
    public CarDTO(){}
}
