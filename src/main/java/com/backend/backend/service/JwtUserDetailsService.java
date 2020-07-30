package com.backend.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.backend.backend.dao.*;
import com.backend.backend.model.UserRoles;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modelcar.CarDTO;
import com.backend.backend.modeldebtor.Debtor;
import com.backend.backend.modelrent.Rent;
import com.backend.backend.modelrent.RentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.model.DAOUser;
import com.backend.backend.model.UserDTO;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRolesDAO userRolesDAO;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private RentDAO rentDAO;

    @Autowired
    private DebtorDAO debtorDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),getAuthority(user));
    }

    public DAOUser save(UserDTO user) {

        DAOUser existingUser = userDao.findByUsername(user.getUsername());
        if(existingUser==null) {
            if (user.getName().length()>1 && user.getAddress().length()>1 && user.getSurname().length()>1 && user.getUsername().length()>1 && user.getPassword().length()>1){
                DAOUser newUser = new DAOUser();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setName(user.getName());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setAddress(user.getAddress());
            UserRoles newRoles = new UserRoles(5);
            userRolesDAO.save(newRoles);
            return userDao.save(newUser);
            }else{return null;}
        }
        else{return null;}
    }

    private Set<SimpleGrantedAuthority> getAuthority(DAOUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }






}