package com.backend.backend.modelarduino;

import com.backend.backend.service.ArduinoDetailsService;

import java.io.Serializable;

public class ArduinoPositionRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150709L;
    private double latitude;
    private double longitude;

    public ArduinoPositionRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ArduinoPositionRequest(){}
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
