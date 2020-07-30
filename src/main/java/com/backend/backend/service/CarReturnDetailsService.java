package com.backend.backend.service;

import com.backend.backend.dao.CarDAO;
import com.backend.backend.dao.DebtorDAO;
import com.backend.backend.dao.NotificationDAO;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modeldebtor.Debtor;
import com.backend.backend.modeldepositpayment.DepositPaidResponse;
import com.backend.backend.modelnotifications.Notification;
import com.backend.backend.modelnotifications.Notifications;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.modelreservation.ReturnCarRequest;
import com.backend.backend.modelreservation.ReturnCarResponse;
import com.sun.deploy.uitoolkit.DelegatingPluginUIToolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class CarReturnDetailsService {

    @Autowired
    private RentDAO rentDAO;
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private DebtorDAO debtorDAO;
    @Autowired
    private DataParser dataParser;
    @Autowired
    private NotificationDAO notificationDAO;


    public static Double calculateCost(Double fuelCost) {
        Random r = new Random();
        int usageCost = 300 + r.nextInt(2000);
        System.out.println("Wygenerowany koszt najmu auta " +usageCost);
        int kilometers = 1000 + r.nextInt(5000);
        System.out.println("Wygenerowane kilometry "+kilometers);
        double cost = ((kilometers / 100) * fuelCost * 6) + usageCost;
        System.out.println("Wygenerowany koszt łączny "+cost);
        return cost;
    }



    public ReturnCarResponse returnBeforeExpiration(Rent rentToCheck, Double fuelCost){
        if(rentToCheck.getRent_cost()==0)
        {
            Car carToReturn = carDAO.findById(rentToCheck.getCar_id());
            carToReturn.setService(false);
            carToReturn.setReservation(false);
            carDAO.save(carToReturn);
            rentToCheck.setRent_cost(calculateCost(fuelCost));
            rentDAO.save(rentToCheck);
            return new ReturnCarResponse(rentToCheck.getCar_id(),rentToCheck.getRent_cost());

        }else{
            return new ReturnCarResponse(rentToCheck.getCar_id(),rentToCheck.getRent_cost());
        }

    }



    public ReturnCarResponse returnCarByUser(ReturnCarRequest returnCarRequest, Authentication authentication){
        LocalDateTime now = LocalDateTime.now();
        //na pewno jest w rents
        if(dataParser.parseSingleDateFromString(returnCarRequest.getEnd_date()).isAfter(now))
        {
            System.out.println(" AUTO ZWRACAMY I UZYTKOWNIK ODDAJE PRZED CZASEM");
            Rent rentToCheck = rentDAO.findSingleRent(returnCarRequest.getCar_id(),authentication.getName(),dataParser.parseSingleDateFromString(returnCarRequest.getStart_date()),dataParser.parseSingleDateFromString(returnCarRequest.getEnd_date()));
            Double fuelCost = rentToCheck.getFuel_cost();
            ReturnCarResponse returnCarResponse = returnBeforeExpiration(rentToCheck,fuelCost);
            return returnCarResponse;

        }else{
            //nie musi byc w rents jesli nie zdazyl przed schedulerem ale moze jesli nie zdazyl
            //improwizacja
            System.out.println(" AUTO ZWRACAMY I UZYTKOWNIK ODDAJE PO CZASIE");
            Rent rentToCheck = rentDAO.findSingleRent(returnCarRequest.getCar_id(),authentication.getName(),dataParser.parseSingleDateFromString(returnCarRequest.getStart_date()),dataParser.parseSingleDateFromString(returnCarRequest.getEnd_date()));
            //Double fuelCost = rentToCheck.getFuel_cost();
            if(rentToCheck!=null)
            {
                //jest w tabeli rents
                Double fuelCost = rentToCheck.getFuel_cost();
                System.out.println(" AUTO ZWRACAMY I UZYTKOWNIK ODDAJE PO CZASIE TABELA RENTS");
                ReturnCarResponse returnCarResponse = returnBeforeExpiration(rentToCheck,fuelCost);
                return returnCarResponse;

            }else{
                //jest w debtorsach
                System.out.println(" AUTO ZWRACAMY I UZYTKOWNIK ODDAJE PO CZASIE TABELA DEBTORS");
                Debtor debtorToCheck = debtorDAO.findSingleDebtor(returnCarRequest.getCar_id(),authentication.getName(),dataParser.parseSingleDateFromString(returnCarRequest.getStart_date()),dataParser.parseSingleDateFromString(returnCarRequest.getEnd_date()));
                return new ReturnCarResponse(debtorToCheck.getCar_id(),debtorToCheck.getRent_cost());
            }

        }
    }


/*

    public void returnCarByCyclicOperation(LocalDateTime now){
        System.out.println("METODA WYWOLANA ");
        List<Rent> rentsToCheck = rentDAO.getAllRents(now,false);

        for(Rent rent : rentsToCheck){
            System.out.println("obslugujemy rent o id"+rent.getId());
            if(rent.getRent_cost()!=0){
                Debtor newDeptor = new Debtor(rent.getCar_id(),
                        rent.getUsername(),
                        rent.getStart_date(),
                        rent.getEnd_date(),
                        rent.isRent_paid(),
                        rent.getRent_cost(),
                        rent.getDeposit(),
                        rent.getFuel_cost(),
                        rent.isDeposit_paid(),
                        rent.getBox_code()
                        );
                System.out.println("RENT MIAL COST WIEC TWORZYMY DEBTORA I USUWAMY RENT Z AUTEM"+rent.getCar_id());
                debtorDAO.save(newDeptor);
                rentDAO.delete(rent);
            }else
            {
                //osoba nie oddała pojazdu wiec tworzymy powiadomienie dla admina

                Notification notification = new Notification(
                        rent.getUsername(),
                        rent.getStart_date(),
                        rent.getEnd_date(),
                        rent.getCar_id()
                );
                notificationDAO.save(notification);


                Car carToReturn = carDAO.findById(rent.getCar_id());
                carToReturn.setReservation(false);
                carToReturn.setService(false);
                carDAO.save(carToReturn);

                rent.setRent_cost(calculateCost(rent.getFuel_cost()));
                rentDAO.save(rent);

                Debtor newDebtor = new Debtor(rent.getCar_id(),
                        rent.getUsername(),
                        rent.getStart_date(),
                        rent.getEnd_date(),
                        rent.isRent_paid(),
                        rent.getRent_cost(),
                        rent.getDeposit(),
                        rent.getFuel_cost(),
                        rent.isDeposit_paid(),
                        rent.getBox_code()
                );
                System.out.println("RENT NIE MIAL COST WIEC TWORZYMY DEBTORA Z WYGENEROWANYM COSTEM I USUWAMY RENT Z AUTEM"+rent.getCar_id());
                System.out.println("ZWRACAMY TEZ AUTO O VINIE "+carToReturn.getVin());
                debtorDAO.save(newDebtor);
                rentDAO.delete(rent);

            }


        }

    }

*/
    public List<Notification> getNotifications()
    {
        return (List<Notification>) notificationDAO.findAll();
    }

    public ResponseEntity<Object> deleteNotification(Integer id){

            Notification notificationToRemove = notificationDAO.findById(id);

            if(notificationToRemove.equals(null)){

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else{
                notificationDAO.delete(notificationToRemove);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
        }




}
