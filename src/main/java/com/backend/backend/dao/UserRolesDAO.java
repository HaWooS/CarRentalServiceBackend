package com.backend.backend.dao;

import com.backend.backend.model.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface UserRolesDAO extends CrudRepository<UserRoles,Long> {
}
