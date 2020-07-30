package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.DebtorDAO;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.dao.UserDao;
import com.backend.backend.model.DAOUser;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modeldebtor.Debtor;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.modelrent.RentDTO;
import com.backend.backend.modelreservation.ReservationRequest;
import com.backend.backend.modelreservation.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ReservationDetailsService {
    @Autowired
    private RentDAO rentDAO;
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DebtorDAO debtorDAO;

    private String usernameFromToken;

    private int car_id;

    private LocalDateTime start_Date;

    private LocalDateTime end_Date;

    public String getUsernameFromToken() {
        return usernameFromToken;
    }

    public void setUsernameFromToken(String usernameFromToken) {
        this.usernameFromToken = usernameFromToken;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public LocalDateTime getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(LocalDateTime start_Date) {
        this.start_Date = start_Date;
    }

    public LocalDateTime getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(LocalDateTime end_Date) {
        this.end_Date = end_Date;
    }

    public LocalDateTime getCurrentDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentDate = LocalDateTime.parse(formatter.format(now),formatter);
        System.out.println(" REZERWACJA DATA" + currentDate);
        return currentDate;
    }

    public LocalDateTime getEndDate(int rent_days){
        LocalDateTime currentDate = getCurrentDate();
        LocalDateTime endDate = currentDate.plusDays(rent_days);
        return endDate;
    }

    public Rent prepareRent(ReservationRequest reservationRequest)
    {
        Rent rent = new Rent();
        rent.setCar_id(reservationRequest.getId());
        setCar_id(rent.getCar_id());
        rent.setUsername(getUsernameFromToken());
        rent.setStart_date(getCurrentDate());
        setStart_Date(rent.getStart_date());
        rent.setEnd_date(getEndDate(reservationRequest.getRent_days()));
        setEnd_Date(rent.getEnd_date());
        rent.setRent_paid(false);
        rent.setRent_cost(0);
        rent.setDeposit(reservationRequest.getDeposit());
        rent.setFuel_cost(reservationRequest.getFuel_cost());
        rent.setDeposit_paid(false);
        rent.setBox_code(0);
        return rentDAO.save(rent);
    }



    public ReservationResponse reserveCar(ReservationRequest reservationRequest)
    {
        Car carToCheck = carDAO.findById(reservationRequest.getId());
        if(carToCheck.isReservation()==false)
        {
            prepareRent(reservationRequest);
            carToCheck.setReservation(true);
            carDAO.save(carToCheck);
            Rent rentToCheck = rentDAO.findByCredentials(reservationRequest.getId(),reservationRequest.getUsername(),getStart_Date());
            System.out.println("DATA "+rentToCheck.getStart_date());
            System.out.println("ID AUTA "+reservationRequest.getId());
            System.out.println("ID UNAME "+reservationRequest.getUsername());
            System.out.println("ID DATA "+ getStart_Date());
            ReservationResponse reservationResponse = new ReservationResponse(rentToCheck.getCar_id(),rentToCheck.getStart_date(),rentToCheck.getEnd_date());
            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                Rent rentToCheckScheduler = rentDAO.findByCredentials(reservationRequest.getId(),reservationRequest.getUsername(),getStart_Date());
                if(rentToCheckScheduler.isDeposit_paid()==false)
                {
                    carToCheck.setReservation(false);
                    carDAO.save(carToCheck); //???????
                    System.out.println("SCHEDULER CZY OPLACONE WIDZI? "+ rentToCheckScheduler.isDeposit_paid());
                    System.out.println("Auto nieoplacone wiec ZWRACAMY je do puli i kasujemy renta");
                    rentDAO.delete(rentToCheckScheduler);
                }
                else{
                    System.out.println("Auto oplacone wiec NIE ZWRACAMY do puli");
                }
                //Car undoReserve = carDAO.findById(id);
                //undoReserve.setReservation(false);
               // Car save = carDAO.save(undoReserve);
                //return save;
            }, 5, TimeUnit.MINUTES);
            executorService.shutdown();
            //return new ResponseEntity<>(reservationResponse,HttpStatus.OK);
            return reservationResponse;
        }else
        {
          //  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return null;
        }



    }
    public RentDTO getRentForSingleUser(Authentication authentication) {
        DAOUser userFromRequest = userDao.findByUsername(authentication.getName());
        //najpierw sprawdzamy Rent
        System.out.println(" USER TO "+ authentication.getName() + userFromRequest.getUsername());
        Rent rentToCheck = rentDAO.findByUsernameAndAndRentpaid(false, userFromRequest.getSurname());
        Debtor debtorToCheck = debtorDAO.findByUsername(userFromRequest.getUsername());
        RentDTO userRent = new RentDTO();
        if (rentToCheck != null) {
            System.out.println("ZNALAZLEM UZYTKOWNIKA I RENTA " + rentToCheck.getUsername() + rentToCheck.getBox_code());
            userRent.setCar_id(rentToCheck.getCar_id());
            userRent.setUsername(rentToCheck.getUsername());
            userRent.setStart_date(rentToCheck.getStart_date());
            userRent.setEnd_date(rentToCheck.getEnd_date());
            userRent.setRent_paid(rentToCheck.isRent_paid());
            userRent.setRent_cost(rentToCheck.getRent_cost());
            userRent.setDeposit(rentToCheck.getDeposit());
            userRent.setFuel_cost(rentToCheck.getFuel_cost());
            userRent.setDeposit_paid(rentToCheck.isDeposit_paid());
            userRent.setBox_code(rentToCheck.getBox_code());
            /*RentDTO userRent = new RentDTO(rentToCheck.getCar_id(),
                    rentToCheck.getUsername(),
                    rentToCheck.getStart_date(),
                    rentToCheck.getEnd_date(),
                    rentToCheck.isRent_paid(),
                    rentToCheck.getRent_cost(),
                    rentToCheck.getDeposit(),
                    rentToCheck.getFuel_cost(),
                    rentToCheck.isDeposit_paid(),
                    rentToCheck.getBox_code());*/
            return userRent;
        } else if ((rentToCheck == null) && (debtorToCheck != null)) {
            //szukamy w Debtorsach
            /*
            RentDTO userRent = new RentDTO(debtorToCheck.getCar_id(),
                    debtorToCheck.getUsername(),
                    debtorToCheck.getStart_date(),
                    debtorToCheck.getEnd_date(),
                    debtorToCheck.isRent_paid(),
                    debtorToCheck.getRent_cost(),
                    debtorToCheck.getDeposit(),
                    debtorToCheck.getFuel_cost(),
                    debtorToCheck.isDeposit_paid(),
                    debtorToCheck.getBox_code());
                    */
            userRent.setCar_id(debtorToCheck.getCar_id());
            userRent.setUsername(debtorToCheck.getUsername());
            userRent.setStart_date(debtorToCheck.getStart_date());
            userRent.setEnd_date(debtorToCheck.getEnd_date());
            userRent.setRent_paid(debtorToCheck.isRent_paid());
            userRent.setRent_cost(debtorToCheck.getRent_cost());
            userRent.setDeposit(debtorToCheck.getDeposit());
            userRent.setFuel_cost(debtorToCheck.getFuel_cost());
            userRent.setDeposit_paid(debtorToCheck.isDeposit_paid());
            userRent.setBox_code(debtorToCheck.getBox_code());
            return userRent;
        } else if ((rentToCheck == null) && (debtorToCheck == null)) {
            System.out.println("NIE ZNALAZŁEM GO NIGDZIE");
            //taka osoba nigdy nic nie wypozyczyla wiec mozna jej na to zezwolic
            return null;
        }
        return userRent;
    }









}
