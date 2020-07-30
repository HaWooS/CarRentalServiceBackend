package com.backend.backend.controllers;
import com.backend.backend.dao.RentDAO;
import com.backend.backend.modelarduino.ArduinoPositionRequest;
import com.backend.backend.modelcar.CarDTO;
import com.backend.backend.modelcar.PartialCarDTO;
import com.backend.backend.modeldepositpayment.DepositPaidRequest;
import com.backend.backend.modelfuel.FuelDTO;
import com.backend.backend.modelrent.RentRequest;
import com.backend.backend.modelreservation.ReservationRequest;
import com.backend.backend.modelreservation.ReturnCarRequest;
import com.backend.backend.modelstatistics.StatisticsRequest;
import com.backend.backend.modepayment.PaymentRequest;
import com.backend.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.backend.backend.config.JwtTokenUtil;
import com.backend.backend.model.JwtRequest;
import com.backend.backend.model.JwtResponse;
import com.backend.backend.model.UserDTO;

import java.text.ParseException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private CarDetailsService carDetailsService;

    @Autowired
    private StatisticsDetailsService statisticsDetailsService;

    @Autowired
    private NotificationsDetailsService notificationsDetailsService;

    @Autowired
    RentDAO rentDAO;

    @Autowired
    DataParser dataParser;

    @Autowired
    private ReservationDetailsService reservationDetailsService;

    @Autowired
    private ArduinoDetailsService arduinoDetailsService;

    @Autowired
    private DepositDetailsService depositDetailsService;

    @Autowired
    private CarReturnDetailsService carReturnDetailsService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;



    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public ResponseEntity<?> getRents(@RequestBody CarDTO car) throws Exception {
        return ResponseEntity.ok(carDetailsService.saveCar(car));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value ="/availableCars", method = RequestMethod.GET)
    public ResponseEntity<?> getAvailableCars() throws Exception{
        return ResponseEntity.ok(carDetailsService.getAvailableCars());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value ="/cars", method = RequestMethod.GET)
    public ResponseEntity<?> getCars() throws Exception{
        return ResponseEntity.ok(carDetailsService.getAllCars());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/cars/{id}")
    public ResponseEntity<?>  partialUpdate(@RequestBody PartialCarDTO partialCar, @PathVariable("id") Integer id){
        return ResponseEntity.ok(carDetailsService.updateCar(partialCar,id));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCar(@PathVariable("id") Integer id){
        return ResponseEntity.ok(carDetailsService.deleteCar(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/getNotifications")
    public ResponseEntity<?> getNotifications(){
        return ResponseEntity.ok(notificationsDetailsService.getNotifications());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/notifications/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Integer id){
        return ResponseEntity.ok(notificationsDetailsService.deleteNotification(id));
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value="/getFuelCost", method = RequestMethod.GET)
    public ResponseEntity<?> getFuelCost() throws Exception{
        return ResponseEntity.ok(carDetailsService.getFuelCost());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value="/updateFuelCost/{id}")
    public ResponseEntity<?> updateFuelCost(@RequestBody FuelDTO fuelDTO,@PathVariable("id") Integer id){
        return ResponseEntity.ok(carDetailsService.updateFuelCost(fuelDTO,id));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/earningStatistics")
    public ResponseEntity<?> getStatistics(@RequestBody StatisticsRequest earningRequest) throws ParseException {
        dataParser.convertRequestDates(earningRequest.getStartDate(),earningRequest.getEndDate());
        return ResponseEntity.ok(rentDAO.getStatistics(dataParser.getParsedStartDate(),dataParser.getParsedEndDate()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/tripStatistics")
    public ResponseEntity<?> getTripStatistics(@RequestBody StatisticsRequest tripRequest) throws ParseException {
        dataParser.convertRequestDates(tripRequest.getStartDate(),tripRequest.getEndDate());
        return ResponseEntity.ok(rentDAO.getTrips(dataParser.getParsedStartDate(),dataParser.getParsedEndDate()));
    }



    @PreAuthorize("hasRole('USER')")
    @PostMapping(value="/reserveCar")
    public ResponseEntity<?> reserveCar(@RequestBody ReservationRequest reservationRequest) throws ParseException{
        return ResponseEntity.ok(reservationDetailsService.reserveCar(reservationRequest));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value="/payDeposit")
    public ResponseEntity<?> payDeposit(@RequestBody DepositPaidRequest depositPaidRequest,Authentication authentication)throws ParseException{
        return ResponseEntity.ok(depositDetailsService.payDeposit(depositPaidRequest,authentication));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value="/returnCar")
    public ResponseEntity<?> returnCarByUser(@RequestBody ReturnCarRequest returnCarRequest, Authentication authentication)throws ParseException{
        return ResponseEntity.ok(carReturnDetailsService.returnCarByUser(returnCarRequest,authentication));
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping(value="/confirmPayment")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest paymentRequest) throws ParseException{
        return ResponseEntity.ok(paymentDetailsService.confirmPayment(paymentRequest));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value="/getRentsForSingleUser")
    public ResponseEntity<?> getRentForSingleUser(Authentication authentication){
        return ResponseEntity.ok(reservationDetailsService.getRentForSingleUser(authentication));
    }

    @PostMapping(value = "/arduino")
    public ResponseEntity<?> updatePosition(@RequestBody ArduinoPositionRequest arduinoPositionRequest){
        return ResponseEntity.ok(arduinoDetailsService.updatePosition(arduinoPositionRequest));
    }



}