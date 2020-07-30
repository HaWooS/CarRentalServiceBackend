package com.backend.backend.dao;

import com.backend.backend.modelfuel.Fuel;
import org.springframework.data.repository.CrudRepository;

public interface FuelDAO extends CrudRepository<Fuel,Long> {
    Fuel findById(Integer id);
}
