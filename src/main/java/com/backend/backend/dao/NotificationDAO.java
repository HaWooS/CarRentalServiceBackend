package com.backend.backend.dao;

import com.backend.backend.modelcar.Car;
import com.backend.backend.modelnotifications.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationDAO extends CrudRepository<Notification,Long> {
    Notification findById(Integer id);
}
