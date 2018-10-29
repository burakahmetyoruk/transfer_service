package com.burak.domain.repository;

import com.burak.domain.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple repository to allow CRUD-Methods for Account.
 * @author byoruk
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
}