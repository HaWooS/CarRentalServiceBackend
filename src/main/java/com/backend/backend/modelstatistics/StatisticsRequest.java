package com.backend.backend.modelstatistics;

import java.io.Serializable;

public class StatisticsRequest implements Serializable{
        private static final long serialVersionUID = 5926468583005150709L;
        private String startDate;
        private String endDate;

        public StatisticsRequest(String startDate, String endDate) {
            this.setStartDate(startDate);
            this.setEndDate(endDate);
        }
        public StatisticsRequest(){}

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
