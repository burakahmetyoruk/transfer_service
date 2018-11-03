package com.burak.domain.repository;

import com.burak.domain.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Simple repository to allow CRUD-Methods for Account.
 * @author byoruk
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByName(String name);
}