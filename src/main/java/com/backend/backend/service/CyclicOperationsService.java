package com.backend.backend.service;
import com.backend.backend.dao.*;
import com.backend.backend.modeldebtor.Debtor;
import com.backend.backend.modeldebtorchecker.DebtorChecker;
import com.backend.backend.modelnotifications.Notification;
import com.backend.backend.modelrent.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.backend.backend.service.CarReturnDetailsService.calculateCost;



@Service
public class CyclicOperationsService {

    @Autowired
    private CarReturnDetailsService carReturnDetailsService;

    @Autowired
    private CarDAO carDAO;

    @Autowired
    private RentDAO rentDAO;

    @Autowired
    private DebtorDAO debtorDAO;

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private DebtorCheckerDAO debtorCheckerDao;

    public CyclicOperationsService(){}


    public void calculateCyclicDebts(){
        LocalDateTime teraz = LocalDateTime.now();
        System.out.println("Wywolalo sie sprawdzanie o 23 codzienne" +teraz);

        List <Debtor> debtors = (List)debtorDAO.findAll();
        for (Debtor debtor: debtors){
            System.out.println("obsluguje debtora nr"+debtor.getId());
            double penalty = 100 + debtor.getRent_cost();
            debtor.setRent_cost(penalty);
            debtorDAO.save(debtor);
            LocalDateTime now = LocalDateTime.now();
            DebtorChecker debtorChecker = debtorCheckerDao.findById(1);
            System.out.println(" w cyclic mamy teraz czas "+ now);
            debtorChecker.setLast_check(now);
            System.out.println("AKTUALIZUEJ CZAS OSTATNIEGO WYWOLANIA NA DZIS");
            debtorCheckerDao.save(debtorChecker);
        }
    }
    public void calculateImmediatelyOverdueDebts(){
        System.out.println("1");
        //jest to zalegla operacja za czas prac serwisowych wiec nie aktualizujemy daty wykonania
        System.out.println("WYKONUJE NATYCHMIASTOWE ZALEGLE KALKULACJE I NIE ZMIENIAM CZASU OSTATNIEGO WYKONANIA");
        List <Debtor> debtors = (List)debtorDAO.findAll();
        for (Debtor debtor: debtors){
            double penalty = 100 + debtor.getRent_cost();
            debtor.setRent_cost(penalty);
            debtorDAO.save(debtor);
        }
    }


    public void checkRentsByCyclicOperation(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println("teraz jest "+now.toString());
        System.out.println("WYWOŁUJĘ CYKLICZNE SPRAWDZENIE RENTS CO 10 MINUT");
        List<Rent> rentsToCheck = rentDAO.getAllRents(now,false);
        System.out.println(rentsToCheck.toString());

        for(Rent rent : rentsToCheck){
            System.out.println("obslugujemy rent o id"+rent.getId());
            if(rent.getRent_cost()!=0){
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
                System.out.println("RENT MIAL COST WIEC TWORZYMY DEBTORA I USUWAMY RENT Z AUTEM"+rent.getCar_id());
                debtorDAO.save(newDebtor);
                rentDAO.delete(rent);
            }else
            {
                System.out.println("TWORZE POWIADOMIENIE DLA ADMINISTRATORA O NOWYM DLUZNIKU");
                //osoba nie oddała pojazdu wiec tworzymy powiadomienie dla admina
                Notification notification = new Notification(
                        rent.getUsername(),
                        rent.getStart_date(),
                        rent.getEnd_date(),
                        rent.getCar_id()
                );
                notificationDAO.save(notification);
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
                debtorDAO.save(newDebtor);
                rentDAO.delete(rent);

            }


        }

    }
    public void runCyclicOperations(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DebtorChecker savedDebtorChecker = debtorCheckerDao.findById(1);

        LocalDateTime lastRun = savedDebtorChecker.getLast_check();

        String now = LocalDateTime.now().format(formatter);
        String yesterday = LocalDateTime.now().minusDays(1).format(formatter);

        System.out.println("WCZORAJ BYL "+yesterday);

        String ydYear=now.substring(0,4);
        String ydMonth=now.substring(5,7);
        String ydDay=now.substring(8,10);
        String ydHoursAndMinutesAndSecondsStart="21:59:00";
        String ydHoursAndMinutesAndSecondsEnd="23:59:59";
        String ydStartString = ydYear+"-"+ydMonth+"-"+ydDay+" "+ydHoursAndMinutesAndSecondsStart;
        String ydEndString = ydYear+"-"+ydMonth+"-"+ydDay+" "+ydHoursAndMinutesAndSecondsEnd;

        LocalDateTime ydStart = LocalDateTime.parse(ydStartString,formatter);
        LocalDateTime ydEnd = LocalDateTime.parse(ydEndString,formatter);
        //ustaw co 10 minutowe sprawdzanie
        ScheduledExecutorService cyclicRentChecker = Executors.newScheduledThreadPool(1);
        cyclicRentChecker.scheduleAtFixedRate(this::checkRentsByCyclicOperation,
                0,
                TimeUnit.MINUTES.toSeconds(10),
                TimeUnit.SECONDS);
        if((lastRun.isBefore(ydEnd))&&(lastRun.isAfter(ydStart))){
            System.out.println("if poszedl");
            System.out.println("ostatnie sprawdzenie było "+lastRun);
            /*sprawdz czy wczoraj pomiedzy 22 a 23 sie wykonalo i jesli tak to ustaw wykonanie
             na cyklicznie dzis 22-23 i do przodu
             czynnosci to naliczenie*/
            System.out.println("OSTATNIO SPRAWDZANO WCZORAJ, USTAWIAM WYKONANIE NA DZIŚ 22-23 I CO DZIEŃ");
           calculateCyclicDebts();
        }else{
            System.out.println("ostatnie sprawdzenie było "+lastRun);
            /*jezeli sie wykonalo wczesniej niz wczoraj to policz natychmiast
            nalezy je wykonac NATYCHMIAST i nie zmieniac czasu ostatniego wykonania
            nie aktualizuj czasu wykonania*/
            System.out.println("NIE WYKONALO SIE WCZORAJ TYLKO WCZESNIEJ, LICZCE OD RAZU I NIE ZMIENIAM CZASU WYWOLANIA");
            calculateImmediatelyOverdueDebts();
            System.out.println("USTAWIAM CYKLICZNE NA 22-23 CO DZIEŃ I ZMIENIAM CZAS WYKONANIA NA DZIS");
            /*dodatkowo nalezy ustawic zadanie cykliczne na 22-23
            nalezy dodac czas wykonania tego zadania na koniec dnia
            czynnosci to naliczenie odsetek*/
            ZonedDateTime nows = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
            System.out.println("Teraz mamy czas nieobciety"+nows);
            String next = nows.toString().substring(0,10);
            System.out.println("Teraz mamy czas obciety "+next);
            ZonedDateTime nextRun = ZonedDateTime.parse(next+"T23:59:00+00:00[Europe/Warsaw]").withHour(23).withMinute(59).withSecond(0);//nows.withHour(0).withMinute(0).withSecond(0);
            System.out.println("Kolejne uruchomienie o "+nextRun);

            Duration duration = Duration.between(nows, nextRun);
            long initalDelay = duration.getSeconds();
            System.out.println("URUCHOMIENIE PRZEWIDZIANE ZA SEKUND " + initalDelay);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(this::calculateCyclicDebts, initalDelay,
                    TimeUnit.DAYS.toSeconds(1),
                    TimeUnit.SECONDS);
        }




    }
}
