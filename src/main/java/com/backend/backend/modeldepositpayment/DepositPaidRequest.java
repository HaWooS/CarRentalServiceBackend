package com.backend.backend.modeldepositpayment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DepositPaidRequest implements Serializable {
    private int id;
    private String start_date;

    public DepositPaidRequest(){}

    public DepositPaidRequest(int id, String start_date) {
        this.id = id;
        this.start_date = start_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
