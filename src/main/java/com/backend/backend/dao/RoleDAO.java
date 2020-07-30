package com.backend.backend.dao;

import com.backend.backend.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleDAO extends CrudRepository<Role, Long> {

    Role findByName(String name);

    Role findByDescription(String description);
}
