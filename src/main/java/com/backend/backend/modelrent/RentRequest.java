package com.backend.backend.modelrent;

import java.io.Serializable;

public class RentRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150709L;
    private String startDate;
    private String endDate;

    public RentRequest(String startDate, String endDate) {
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }
    public RentRequest(){}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

