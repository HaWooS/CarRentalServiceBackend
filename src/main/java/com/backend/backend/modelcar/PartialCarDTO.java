package com.backend.backend.modelcar;

public class PartialCarDTO {


        private String register_number;

        private Double deposit;

        private Double latitude;

        private Double longitude;

        private boolean service;

        private boolean reservation;

    public PartialCarDTO(String register_number, Double deposit, Double latitude, Double longitude, boolean service, boolean reservation) {
        this.register_number = register_number;
        this.deposit = deposit;
        this.latitude = latitude;
        this.longitude = longitude;
        this.service = service;
        this.reservation = reservation;
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

             public boolean getService() {
            return service;
        }

             public void setService(Boolean service) {
            this.service = service;
        }

             public boolean getReservation() {
            return reservation;
        }

             public void setReservation(Boolean reservation) {
            this.reservation = reservation;
        }
}
