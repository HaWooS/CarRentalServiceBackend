package com.backend.backend.service;

import com.backend.backend.dao.NotificationDAO;
import com.backend.backend.modelcar.Car;
import com.backend.backend.modelnotifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsDetailsService {
    @Autowired
    NotificationDAO notificationDAO;

    public List<Notification> getNotifications(){
        return (List)notificationDAO.findAll();
    }

    public ResponseEntity<Object> deleteNotification(Integer id){
        Notification notificationToDelete = notificationDAO.findById(id);
        if(notificationToDelete.equals(null))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            notificationDAO.delete(notificationToDelete);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

    }

}
