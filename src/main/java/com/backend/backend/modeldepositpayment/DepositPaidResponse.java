package com.backend.backend.modeldepositpayment;

import java.io.Serializable;

public class DepositPaidResponse implements Serializable {
    private int box_code;

    public int getBox_code() {
        return box_code;
    }

    public void setBox_code(int box_code) {
        this.box_code = box_code;
    }

    public DepositPaidResponse(int box_code) {
        this.box_code = box_code;
    }

    public DepositPaidResponse(){}
}
