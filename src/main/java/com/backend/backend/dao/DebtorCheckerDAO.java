package com.backend.backend.dao;

import com.backend.backend.modeldebtorchecker.DebtorChecker;
import org.springframework.data.repository.CrudRepository;

public interface DebtorCheckerDAO extends CrudRepository<DebtorChecker, Long> {

    DebtorChecker findById(Integer id);
}
